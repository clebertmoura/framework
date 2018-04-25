/**
 * 
 */
package br.com.framework.pilotojee7.push.consumer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang3.StringUtils;
import org.jboss.aerogear.unifiedpush.exception.PushSenderException;
import org.jboss.aerogear.unifiedpush.message.MessageResponseCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.push.api.UnifiedPushServer;

/**
 * Consumidor JMS para envio das mensagens de notificação push.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@MessageDriven(name = "PushSenderConsumer", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/pilotojee7-queue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "AUTO_ACKNOWLEDGE"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "5"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "ConsumerName = 'PushSenderConsumer'")})
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PushSenderConsumer implements MessageListener, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String KEY_ALIASES = "aliases";
	public static final String KEY_VARIANTS = "variants";
	public static final String KEY_DEVICETYPES = "deviceTypes";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_SOUND = "sound";
	public static final String KEY_BADGE = "badge";
	public static final String KEY_TTL = "timeToLive";
	/**
	 * iOS specific: true or false
	 * When this key is present, the system wakes up your app in the background and delivers the notification to its app delegate. 
	 */
	public static final String KEY_CONTENT_AVAILABLE = "contentAvailable";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PushSenderConsumer.class);
	
	@EJB
	private UnifiedPushServer unifiedPushServer;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(Message message) {
		MapMessage msg = (MapMessage) message;
		try {
			
			final String aliases = msg.propertyExists(KEY_ALIASES) ? msg.getStringProperty(KEY_ALIASES) : null;
			final String variants = msg.propertyExists(KEY_VARIANTS) ? msg.getStringProperty(KEY_VARIANTS) : null;
			final String deviceTypes = msg.propertyExists(KEY_DEVICETYPES) ? msg.getStringProperty(KEY_DEVICETYPES) : null;
			final String messageStr = msg.getStringProperty(KEY_MESSAGE);
			final String sound = msg.propertyExists(KEY_SOUND) ? msg.getStringProperty(KEY_SOUND) : null;
			final String badge = msg.propertyExists(KEY_BADGE) ? msg.getStringProperty(KEY_BADGE) : null;
			final Integer timeToLive = msg.propertyExists(KEY_TTL) ? msg.getIntProperty(KEY_TTL) : null;
			final Boolean contentAvailable = msg.propertyExists(KEY_CONTENT_AVAILABLE) ? msg.getBooleanProperty(KEY_CONTENT_AVAILABLE) : null;
			
			Enumeration<String> mapNames = msg.getMapNames();
			Map<String, Object> mapAttributes = new HashMap<String, Object>();
			while (mapNames.hasMoreElements()) {
				String name = mapNames.nextElement();
				Object value = msg.getObject(name);
				mapAttributes.put(name, value);
			}
			
			List<String> aliasesList = null;
			if (aliases != null) {
				aliasesList = Arrays.asList(StringUtils.split(aliases, ","));
			}
			List<String> variantsList = null;
			if (variants != null) {
				variantsList = Arrays.asList(StringUtils.split(variants, ","));
			}
			List<String> deviceTypesList = null;
			if (deviceTypes != null) {
				deviceTypesList = Arrays.asList(StringUtils.split(deviceTypes, ","));
			}
			
			unifiedPushServer.sendSync(variantsList, aliasesList, deviceTypesList, messageStr, sound, badge, timeToLive, contentAvailable, mapAttributes, 
				new MessageResponseCallback() {
					@Override
					public void onComplete() {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Notificacao completada: " + messageStr);
						}
					}
				});
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
