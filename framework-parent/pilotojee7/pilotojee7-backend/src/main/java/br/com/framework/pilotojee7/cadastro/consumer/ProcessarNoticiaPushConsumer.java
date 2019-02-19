package br.com.framework.pilotojee7.cadastro.consumer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.jboss.aerogear.unifiedpush.exception.PushSenderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.pilotojee7.backend.keycloak.dao.KeycloakUsuarioDao;
import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakUsuario;
import br.com.framework.pilotojee7.cadastro.dao.DispositivoDao;
import br.com.framework.pilotojee7.cadastro.dao.NoticiaDao;
import br.com.framework.pilotojee7.cadastro.domain.Dispositivo;
import br.com.framework.pilotojee7.cadastro.domain.Noticia;
import br.com.framework.pilotojee7.push.util.PushType;
import br.com.framework.push.api.UnifiedPushServer;

/**
 * Consumidor JMS para envio das mensagens de notificação push.
 * 
 * @author Cleber Moura <cleber.moura@trinitysolucoes.com>
 *
 */
@MessageDriven(name = "ProcessarNoticiaPushConsumer", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/pilotojee7-queue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "AUTO_ACKNOWLEDGE"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "5"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "ConsumerName = 'ProcessarNoticiaPushConsumer'")})
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ProcessarNoticiaPushConsumer implements MessageListener, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String KEY_ID_NOTICIA = "idNoticia";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessarNoticiaPushConsumer.class);
	
	private static final int BLOCK_SIZE = 100;
	
	@EJB
	private UnifiedPushServer unifiedPushServer;
	
	@Inject
	private KeycloakUsuarioDao keycloakUsuarioDao;
	
	@Inject
	private DispositivoDao dispositivoDao;
	
	@Inject
	private NoticiaDao noticiaDao;
	
	@Override
	public void onMessage(Message message) {
		MapMessage msg = (MapMessage) message;
		try {
			final Long idNoticia = msg.propertyExists(KEY_ID_NOTICIA) ? msg.getLongProperty(KEY_ID_NOTICIA) : null;
			Noticia noticia = noticiaDao.findById(idNoticia).getUniqueResult();
			if (noticia != null) {
				Long countByGruposDaNoticia = keycloakUsuarioDao.getCountByGruposDaNoticia(idNoticia);
				if (countByGruposDaNoticia > 0) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug(String.format("Foram encontrados %s usuarios com permissao para recebimento do Push. Noticia [%s]", countByGruposDaNoticia, idNoticia));
					}
					int pages = (int) Math.ceil(countByGruposDaNoticia / BLOCK_SIZE);
					pages = (pages == 0) ? 1 : pages;
					int currentPage = 0;
					while (currentPage < pages) {
						int currentRow = currentPage * BLOCK_SIZE;
						List<KeycloakUsuario> usuarios = keycloakUsuarioDao.findByGruposDaNoticia(idNoticia, currentRow, BLOCK_SIZE);
						if (!usuarios.isEmpty()) {
							for (KeycloakUsuario keycloakUsuario : usuarios) {
								List<Dispositivo> dispositivos = dispositivoDao.findByUsuario(keycloakUsuario);
								if (!dispositivos.isEmpty()) {
									List<String> aliases = new ArrayList<String>();
									for (Dispositivo dispositivo : dispositivos) {
										aliases.add(dispositivo.getAlias(keycloakUsuario));
									}
									Map<String, Object> attributes = new HashMap<>();
									attributes.put(PushType.class.getSimpleName(), PushType.NOTICIA.name());
									attributes.put("id", noticia.getId());
									attributes.put("titulo", noticia.getTitulo());
									attributes.put("descricao", noticia.getDescricao());
									attributes.put("destaque", noticia.getDestaque().name());
									unifiedPushServer.send(aliases, noticia.getTitulo() + "\n" + noticia.getDescricao(), attributes);
									if (LOGGER.isDebugEnabled()) {
										LOGGER.debug(String.format("Push enviado aos dispositivos do usuario: %s. Noticia [%s]", keycloakUsuario.getLogin(), idNoticia));
									}
								} else {
									if (LOGGER.isDebugEnabled()) {
										LOGGER.debug(String.format("Usuario: %s, nao possui dispositivos ativos para recebimento do Push. Noticia [%s]", keycloakUsuario.getLogin(), idNoticia));
									}
								}
							}
						}
						currentPage++;
					}
				} else {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Nenhum usuario com permissao na noticia: " + idNoticia);
					}
				}
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Noticia nao encontrada: " + idNoticia);
				}
			}
			
		} catch (JMSException e) {
			LOGGER.error("Nao foi possivel recuperar as propriedades na mensagem.", e);
		} catch (EJBTransactionRolledbackException e) {
			Exception causedByException = e.getCausedByException();
			LOGGER.error("Nao foi possivel enviar a mensagem ao UnifiedPushServer. Será feita nova tentativa.", e);
			if (causedByException != null && causedByException instanceof PushSenderException) {
				throw e;
			}
		} catch (RuntimeException e) {
			LOGGER.error("Nao foi possivel enviar a mensagem ao PushServer.", e);
		}
	}
	
}
