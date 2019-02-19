package br.com.framework.push.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jboss.aerogear.unifiedpush.message.MessageResponseCallback;
import org.jboss.aerogear.unifiedpush.message.UnifiedMessage;

/**
 * Interface de integração com o Push Server
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
public interface UnifiedPushServer extends Serializable{
	
	/**
	 * Constantes das propriedades de configuração do push server.
	 */
	public static String PUSH_SERVER = "PUSH_SERVER";
	public static String PUSH_APPLICATION_ID = "PUSH_APPLICATION_ID";
	public static String PUSH_MASTER_SECRET = "PUSH_MASTER_SECRET";
	/**
	 * Time-to-live do push. Tempo em segundos.
	 */
	public static String PUSH_DEFAULT_TTL = "PUSH_DEFAULT_TTL";
	public static String PUSH_DEFAULT_SOUND = "PUSH_DEFAULT_SOUND";
	public static String PUSH_DEFAULT_BADGE = "PUSH_DEFAULT_BADGE";
	public static String PUSH_DEFAULT_CONTENT_AVAILABLE = "PUSH_DEFAULT_CONTENT_AVAILABLE";
	
	/**
	 * Envia o Push de forma assíncrona.
	 * 
	 * @param aliases
	 * @param message
	 * @param attributes
	 */
	public void send(List<String> aliases, String message, Map<String, Object> attributes);
	
	/**
	 * Envia o Push de forma assíncrona.
	 * 
	 * @param variants
	 * @param aliases
	 * @param deviceTypes
	 * @param message
	 * @param attributes
	 */
	public void send(List<String> variants, List<String> aliases, List<String> deviceTypes, String message, Map<String, Object> attributes);
	
	/**
	 * Envia o Push de forma assíncrona.
	 * 
	 * @param variants
	 * @param aliases
	 * @param deviceTypes
	 * @param message
	 * @param sound
	 * @param badge
	 * @param timeToLive
	 * @param attributes
	 */
	public void send(List<String> variants, List<String> aliases, List<String> deviceTypes, String message,
			String sound, String badge, int timeToLive, Map<String, Object> attributes);
	
	/**
	 * Envia o Push de forma assíncrona.
	 * 
	 * @param variants
	 * @param aliases
	 * @param deviceTypes
	 * @param message
	 * @param sound
	 * @param badge
	 * @param timeToLive
	 * @param contentAvailable
	 * @param attributes
	 */
	public void send(List<String> variants, List<String> aliases, List<String> deviceTypes, String message,
			String sound, String badge, int timeToLive, Boolean contentAvailable, Map<String, Object> attributes);
	
	/**
	 * Envia o Push de forma síncrona.
	 * 
	 * @param variants
	 * @param aliases
	 * @param deviceTypes
	 * @param message
	 * @param sound
	 * @param badge
	 * @param timeToLive
	 * @param contentAvailable
	 * @param attributes
	 * @param messageResponseCallback
	 */
	public void sendSync(List<String> variants, List<String> aliases, List<String> deviceTypes, String message,
			String sound, String badge, int timeToLive, Boolean contentAvailable, Map<String, Object> attributes, MessageResponseCallback messageResponseCallback);
	
	/**
	 * Envia o Push de forma assíncrona.
	 * 
	 * @param unifiedMessage
	 * @param messageResponseCallback
	 */
	public void send(UnifiedMessage unifiedMessage, MessageResponseCallback messageResponseCallback);

	/**
	 * Constroi um objeto {@link UnifiedMessage} baseado nos parametros.
	 * 
	 * @param variants
	 * @param aliases
	 * @param deviceTypes
	 * @param message
	 * @param sound
	 * @param badge
	 * @param timeToLive
	 * @param contentAvailable
	 * @param attributes
	 * @return
	 */
	public UnifiedMessage buildUnifiedMessage(List<String> variants, List<String> aliases, List<String> deviceTypes,
			String message, String sound, String badge, int timeToLive, Boolean contentAvailable, Map<String, Object> attributes);
	
}