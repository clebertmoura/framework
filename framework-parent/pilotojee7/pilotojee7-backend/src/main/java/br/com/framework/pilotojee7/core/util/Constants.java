/**
 * 
 */
package br.com.framework.pilotojee7.core.util;

/**
 * Classe que contem as contantes do sistema.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public abstract class Constants extends br.com.framework.model.util.Constants{
	
	public static final String CONFIG_FILENAME = "config.properties";
	
	/**
	 * Constants de tempo
	 */
	public static final long UM_MINUTO_EM_MILLIS = 60000;
	public static final long UMA_HORA_EM_MILLIS = 60 * UM_MINUTO_EM_MILLIS;
	public static final long UM_DIA_EM_MILLIS = 24 * UMA_HORA_EM_MILLIS;
	public static final long UMA_SEMANA_EM_MILLIS = 7 * UM_DIA_EM_MILLIS;
	
	/**
	 * Formatos de data
	 */
	public static final String FORMAT_ddMMyyyy_HHmmss = "ddMMyyyy HHmmss";
	public static final String[] DATE_FORMATS = {
		"dd/MM/yyyy HH:mm:ss.Z", "dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy HH:mm", "dd/MM/yyyy",
		"dd/MM/yy HH:mm:ss.Z", "dd/MM/yy HH:mm:ss", "dd/MM/yy HH:mm", "dd/MM/yy",
		"yyyy/MM/dd HH:mm:ss.Z", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd",
		"yy/MM/dd HH:mm:ss.Z", "yy/MM/dd HH:mm:ss", "yy/MM/dd HH:mm", "yy/MM/dd",
		};
	
	/**
	 * Constants referentes ao login.
	 */
	public static final int LOGIN_MAX_TENTATIVAS = 3;
	public static final int LOGIN_MAX_TENTATIVAS_IP = 5;
	public static final long LOGIN_TEMPO_BLOQUEIO = 30 * UM_MINUTO_EM_MILLIS;
	public static final long LOGIN_TEMPO_BLOQUEIO_IP = 60 * UM_MINUTO_EM_MILLIS;
	public static final long LOGIN_TEMPO_RENOVA_SENHA = 90 * UM_DIA_EM_MILLIS;
	public static final String LOGIN_REGEX_PATTERN = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=[\\S]+$).{8,})";
	
	/**
	 * Keycloak
	 */
	public static final String KEYCLOAK_AUTHSERVERURL= "keycloak.auth-server-url";
	public static final String KEYCLOAK_REALM= "keycloak.realm";
	public static final String KEYCLOAK_USERNAME= "keycloak.username";
	public static final String KEYCLOAK_PASSWORD= "keycloak.password";
	public static final String KEYCLOAK_CLIENTID= "keycloak.clientId";
	public static final String KEYCLOAK_CLIENTSECRET= "keycloak.clientSecret";
	
	/**
	 * App System Base Path
	 */
	public static final String APP_SYSTEM_BASEPATH = "app.system.basePath";
	public static final String APP_RELATORIOS_PATH = "relatorios";
	
}
