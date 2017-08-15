package br.com.framework.push.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.jboss.aerogear.unifiedpush.DefaultPushSender;
import org.jboss.aerogear.unifiedpush.PushSender;
import org.jboss.aerogear.unifiedpush.message.MessageResponseCallback;
import org.jboss.aerogear.unifiedpush.message.UnifiedMessage;
import org.jboss.aerogear.unifiedpush.message.UnifiedMessage.MessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.push.api.UnifiedPushServer;

/**
 * Implementação abstrata do Push Server. O projeto deve implementar essa classe. 
 * 
 * @author Cleber Moura <cleber.moura@trinitysolucoes.com>
 *
 */
public abstract class BaseUnifiedPushServerImpl implements UnifiedPushServer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static Logger LOGGER;
	
	public static final int HORA_EM_SEGUNDOS = 3600;
	public static final int DIA_EM_SEGUNDOS = 24 * HORA_EM_SEGUNDOS;
	public static final int DEFAULT_TTL = 2 * DIA_EM_SEGUNDOS;
	public static final boolean DEFAULT_CONTENT_AVAILABLE = true;

	public String pushServer = "";
	public String pushApplicationId = "";
	public String pushMasterSecret = "";
	public int pushDefaultTtl = DEFAULT_TTL;
	public String pushDefaultSound = "";
	public String pushDefaultBadge = "";
	public boolean pushDefaultContentAvailable = DEFAULT_CONTENT_AVAILABLE;

	protected PushSender sender;

	public BaseUnifiedPushServerImpl() {
		LOGGER = LoggerFactory.getLogger(getClass());
	}
	
	/**
	 * Este método deve ser implementado na classe concreta e retornar um {@link Properties} 
	 * com as configurações do Push Server.
	 * 
	 * @return
	 */
	protected abstract Properties getConfig();
	

	@PostConstruct
	protected void init() {
		pushServer = getConfig().getProperty(PUSH_SERVER);
		pushApplicationId = getConfig().getProperty(PUSH_APPLICATION_ID);
		pushMasterSecret = getConfig().getProperty(PUSH_MASTER_SECRET);
		
		String propPushDefaultTtl = getConfig().getProperty(PUSH_DEFAULT_TTL);
		if (propPushDefaultTtl != null && !propPushDefaultTtl.isEmpty()) {
			pushDefaultTtl = Integer.parseInt(propPushDefaultTtl);
		}
		pushDefaultSound = getConfig().getProperty(PUSH_DEFAULT_SOUND);
		pushDefaultBadge = getConfig().getProperty(PUSH_DEFAULT_BADGE);
		
		String propPushDefaultContentAvailable = getConfig().getProperty(PUSH_DEFAULT_CONTENT_AVAILABLE);
		if (propPushDefaultContentAvailable != null && !propPushDefaultContentAvailable.isEmpty()) {
			pushDefaultContentAvailable = Boolean.parseBoolean(propPushDefaultContentAvailable);
		}
		
		sender = DefaultPushSender.withRootServerURL(pushServer).pushApplicationId(pushApplicationId).masterSecret(pushMasterSecret).build();
	}
	
	/* (non-Javadoc)
	 * @see br.com.framework.push.api.UnifiedPushServer#send(java.util.List, java.lang.String, java.util.Map)
	 */
	@Override
	public void send(List<String> aliases, String message, Map<String, Object> attributes) {
		send(null, aliases, null, message, attributes);
	}

	/* (non-Javadoc)
	 * @see br.com.framework.push.api.UnifiedPushServer#send(java.util.List, java.util.List, java.util.List, java.lang.String, java.util.Map)
	 */
	@Override
	public void send(List<String> variants, List<String> aliases, List<String> deviceTypes, String message, Map<String, Object> attributes) {
		send(variants, aliases, deviceTypes, message, pushDefaultSound, pushDefaultBadge, pushDefaultTtl, pushDefaultContentAvailable, attributes);
	}

	/* (non-Javadoc)
	 * @see br.com.framework.push.api.UnifiedPushServer#send(java.util.List, java.util.List, java.util.List, java.lang.String, java.lang.String, java.lang.String, int, java.util.Map)
	 */
	@Override
	public void send(List<String> variants, List<String> aliases, List<String> deviceTypes, String message,
			String sound, String badge, int timeToLive, Map<String, Object> attributes) {
		send(variants, aliases, deviceTypes, message, sound, badge, timeToLive, pushDefaultContentAvailable, attributes);
	}

	/* (non-Javadoc)
	 * @see br.com.framework.push.api.UnifiedPushServer#send(java.util.List, java.util.List, java.util.List, java.lang.String, java.lang.String, java.lang.String, int, java.lang.Boolean, java.util.Map, org.jboss.aerogear.unifiedpush.message.MessageResponseCallback)
	 */
	@Override
	public void sendSync(List<String> variants, List<String> aliases, List<String> deviceTypes, String message,
			String sound, String badge, int timeToLive, Boolean contentAvailable, Map<String, Object> attributes,
			MessageResponseCallback messageResponseCallback) {
		UnifiedMessage unifiedMessage = buildUnifiedMessage(variants, aliases, deviceTypes, message, sound, badge, timeToLive, contentAvailable, attributes);
		send(unifiedMessage, messageResponseCallback);
	}

	/* (non-Javadoc)
	 * @see br.com.framework.push.api.UnifiedPushServer#send(org.jboss.aerogear.unifiedpush.message.UnifiedMessage, org.jboss.aerogear.unifiedpush.message.MessageResponseCallback)
	 */
	@Override
	public void send(UnifiedMessage unifiedMessage, MessageResponseCallback messageResponseCallback) {
		if (sender == null) {
			LOGGER.error("O PushServer não foi inicializado. As mensagens não serão enviadas.");
			return;
		}
		if (messageResponseCallback != null) {
			sender.send(unifiedMessage, messageResponseCallback);
		} else {
			sender.send(unifiedMessage);
		}
	}

	/* (non-Javadoc)
	 * @see br.com.framework.push.api.UnifiedPushServer#buildUnifiedMessage(java.util.List, java.util.List, java.util.List, java.lang.String, java.lang.String, java.lang.String, int, java.util.Map)
	 */
	@Override
	public UnifiedMessage buildUnifiedMessage(List<String> variants, List<String> aliases, List<String> deviceTypes,
			String message, String sound, String badge, int timeToLive, Boolean contentAvailable, Map<String, Object> attributes) {
		MessageBuilder messageBuilder = UnifiedMessage.withMessage();
		if (contentAvailable != null && contentAvailable) {
			messageBuilder.apns().contentAvailable();
		}
		messageBuilder
			.alert(message).sound(sound).badge(badge).userData(attributes)
			.criteria().aliases(aliases).variants(variants).deviceType(deviceTypes)
			.config().timeToLive(timeToLive);
		return messageBuilder.build();
	}

}
