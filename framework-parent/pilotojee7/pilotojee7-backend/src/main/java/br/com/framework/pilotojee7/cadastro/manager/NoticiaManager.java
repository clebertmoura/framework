package br.com.framework.pilotojee7.cadastro.manager;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
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

import br.com.framework.model.exception.ModelException;
import br.com.framework.pilotojee7.backend.keycloak.manager.KeycloakAdminManager;
import br.com.framework.pilotojee7.cadastro.consumer.ProcessarNoticiaPushConsumer;
import br.com.framework.pilotojee7.cadastro.dao.NoticiaDao;
import br.com.framework.pilotojee7.cadastro.domain.Noticia;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;
import br.com.framework.pilotojee7.core.qualifier.EntityOperation;
import br.com.framework.pilotojee7.core.qualifier.OperationType;

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