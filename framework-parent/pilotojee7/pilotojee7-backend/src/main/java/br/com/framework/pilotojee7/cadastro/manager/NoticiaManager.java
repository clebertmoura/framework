package br.com.framework.pilotojee7.cadastro.manager;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.keycloak.representations.idm.GroupRepresentation;

import br.com.framework.model.exception.ModelException;
import br.com.framework.pilotojee7.backend.keycloak.manager.KeycloakAdminManager;
import br.com.framework.pilotojee7.cadastro.consumer.ProcessarNoticiaPushConsumer;
import br.com.framework.pilotojee7.cadastro.dao.NoticiaDao;
import br.com.framework.pilotojee7.cadastro.domain.Noticia;
import br.com.framework.pilotojee7.cadastro.service.resource.NoticiaResource;
import br.com.framework.pilotojee7.core.enums.SimNao;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;
import br.com.framework.pilotojee7.core.qualifier.EntityOperation;
import br.com.framework.pilotojee7.core.qualifier.OperationType;
import br.com.framework.service.impl.SynchronizeResponse;

/**
 * Manager da entidade Noticia.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class NoticiaManager extends AppBaseManagerImpl<Long, Noticia, NoticiaDao> {
	
	@Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext context;
     
    @Resource(mappedName = "java:/jms/queue/qca-queue")
    private Queue queue;
	
	@Inject @EntityOperation(operation = OperationType.Insert)
	private Event<Noticia> insertEvent;
	
	@Inject @EntityOperation(operation = OperationType.Update)
	private Event<Noticia> updateEvent;
	
	@Inject @EntityOperation(operation = OperationType.Delete)
	private Event<Noticia> deleteEvent;
	
	@Resource
	private SessionContext sessionContext;
	
	@Inject
	private KeycloakAdminManager keycloakAdminManager;

	public NoticiaManager() {
		super(Noticia.class);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(NoticiaDao searchable) {
		super.setSearch(searchable);
	}
	
	/**
	 * Sincroniza as {@link Noticia} 
	 * 
	 * @param lastSync
	 * @param destaque
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SynchronizeResponse<NoticiaResource> sincronizarNoticias(Long lastSync, SimNao destaque, Integer first, Integer max, List<Long> idsCategorias, String titulo) {
		Principal userPrincipal = sessionContext.getCallerPrincipal();
		List<GroupRepresentation> groupsByUser = keycloakAdminManager.getUserGroupsByUserId(userPrincipal.getName());
		List<String> grupos = new ArrayList<>();
		for (GroupRepresentation groupRepresentation : groupsByUser) {
			grupos.add(groupRepresentation.getName());
		}
		
		LocalDateTime dataUltSinc = null;
		if (lastSync != null) {
			dataUltSinc = LocalDateTime.ofInstant(Instant.ofEpochMilli(lastSync), ZoneId.systemDefault());
		}
		
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime dataIntervalo = localDateTime.minusDays(60);
		
		List<Noticia> inseridas = getSearch().getInseridas(dataUltSinc, dataIntervalo, grupos, destaque, first, max, idsCategorias, titulo);
		List<Noticia> atualizadas = getSearch().getAtualizadas(dataUltSinc, dataIntervalo, null, grupos, first, max);
		List<Noticia> deletadas = getSearch().getDeletadas(dataUltSinc, dataIntervalo, grupos);
		List<Noticia> expiradas = getSearch().getExpiradas(dataUltSinc, dataIntervalo, grupos);
		
		SynchronizeResponse<NoticiaResource> syncResource = new SynchronizeResponse<NoticiaResource>();
		syncResource.setDatetimeSync(localDateTime);
		for (Noticia noticia : inseridas) {
			syncResource.getInserted().add(new NoticiaResource(noticia, false, false));
		}
		for (Noticia noticia : atualizadas) {
			syncResource.getUpdated().add(new NoticiaResource(noticia, false, false));
		}
		for (Noticia noticia : deletadas) {
			syncResource.getDeletedIds().add(noticia.getId());
		}
		for (Noticia noticia : expiradas) {
			syncResource.getExpiredIds().add(noticia.getId());
		}
		
		return syncResource;
	}
	
	@Override
	public Noticia insert(Noticia entity) throws PersistenceException, ConstraintViolationException, ModelException {
		entity = super.insert(entity);
		insertEvent.fire(entity);
		return entity;
	}
	
	@Override
	public Noticia update(Noticia entity) throws PersistenceException, ConstraintViolationException, ModelException {
		Noticia noticia = super.update(entity);
		if (noticia.isActive()) {
			updateEvent.fire(noticia);
		}
		return noticia;
	}
	
	/**
	 * Método que observa a inserção de uma notícia
	 * @param noticia
	 */
	public void listenToNoticiaInsert(
		@Observes(during = TransactionPhase.AFTER_SUCCESS) @EntityOperation(operation = OperationType.Insert) Noticia noticia) {
		publicarNoticiaPush(noticia);
	}
	
	/**
	 * Método que observa a inserção de uma notícia
	 * @param noticia
	 */
	public void listenToNoticiaUpdate(
		@Observes(during = TransactionPhase.AFTER_SUCCESS) @EntityOperation(operation = OperationType.Update) Noticia noticia) {
		publicarNoticiaPush(noticia);
	}

	/**
	 * Publica a mensagem na fila JMS para processamento assíncrono.
	 * 
	 * @param noticia
	 */
	protected void publicarNoticiaPush(Noticia noticia) {
		try {
			// cria a mensagem JMS
			MapMessage mapMessage = context.createMapMessage();
			// seta a propriedade ConsumerName para determinar qual o consumidor dessa mensagem.
			mapMessage.setStringProperty("ConsumerName", ProcessarNoticiaPushConsumer.class.getSimpleName());
			// seta as propriedades da mensagem conforme os parâmetros
			mapMessage.setLongProperty(ProcessarNoticiaPushConsumer.KEY_ID_NOTICIA, noticia.getId());
			context.createProducer().send(queue, mapMessage);
			if (logger.isDebugEnabled()) {
				logger.debug("Mensagem publicada na fila para envio do Push.");
			}
		} catch (JMSException e) {
			logger.error("Nao foi possivel publicar a mensagem JMS.", e);
		}
	}

}