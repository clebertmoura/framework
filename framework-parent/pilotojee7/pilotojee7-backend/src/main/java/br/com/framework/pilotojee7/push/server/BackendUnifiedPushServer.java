/**
 * 
 */
package br.com.framework.pilotojee7.push.server;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;

import org.apache.commons.lang3.StringUtils;

import br.com.framework.model.qualifiers.AppConfig;
import br.com.framework.pilotojee7.push.consumer.PushSenderConsumer;
import br.com.framework.push.api.UnifiedPushServer;
import br.com.framework.push.impl.BaseUnifiedPushServerImpl;

/**
 * Implementação concreta do Push Server
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Stateless
@Local(UnifiedPushServer.class)
public class BackendUnifiedPushServer extends BaseUnifiedPushServerImpl {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject @AppConfig
	private Properties appConfig;
	
	@Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext context;
     
    @Resource(mappedName = "java:/jms/queue/pilotojee7-queue")
    private Queue queue;

	/**
	 * 
	 */
	public BackendUnifiedPushServer() {
		// TODO EntityOperation-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see br.com.framework.push.impl.BaseUnifiedPushServerImpl#getConfig()
	 */
	@Override
	protected Properties getConfig() {
		return appConfig;
	}

	@Override
	public void send(List<String> variants, List<String> aliases, List<String> deviceTypes, String message,
			String sound, String badge, int timeToLive, Boolean contentAvailable, Map<String, Object> attributes) {
		try {
			// cria a mensagem JMS
			MapMessage mapMessage = context.createMapMessage();
			// seta a propriedade ConsumerName para determinar qual o consumidor dessa mensagem.
			mapMessage.setStringProperty("ConsumerName", PushSenderConsumer.class.getSimpleName());
			// seta as propriedades da mensagem conforme os parâmetros
			mapMessage.setStringProperty(PushSenderConsumer.KEY_VARIANTS, StringUtils.join(variants, ","));
			mapMessage.setStringProperty(PushSenderConsumer.KEY_ALIASES, StringUtils.join(aliases, ","));
			mapMessage.setStringProperty(PushSenderConsumer.KEY_DEVICETYPES, StringUtils.join(deviceTypes, ","));
			mapMessage.setStringProperty(PushSenderConsumer.KEY_MESSAGE, message);
			mapMessage.setStringProperty(PushSenderConsumer.KEY_SOUND, sound);
			mapMessage.setStringProperty(PushSenderConsumer.KEY_BADGE, badge);
			mapMessage.setIntProperty(PushSenderConsumer.KEY_TTL, timeToLive);
			mapMessage.setBooleanProperty(PushSenderConsumer.KEY_CONTENT_AVAILABLE, contentAvailable);
			 
			if (attributes != null && !attributes.isEmpty()) {
				for (Entry<String, Object> entry : attributes.entrySet()) {
					mapMessage.setObject(entry.getKey(), entry.getValue());
				}
			}
			context.createProducer().send(queue, mapMessage);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Mensagem publicada na fila para envio do Push.");
			}
		} catch (JMSException e) {
			LOGGER.error("Nao foi possivel publicar a mensagem JMS.", e);
		}
	}

}
