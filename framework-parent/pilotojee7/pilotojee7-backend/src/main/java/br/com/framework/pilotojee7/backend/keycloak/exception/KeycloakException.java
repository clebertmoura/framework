package br.com.framework.pilotojee7.backend.keycloak.exception;

/**
 * @author cleber
 *
 */
public class KeycloakException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KeycloakException() {
		// TODO Auto-generated constructor stub
	}

	public KeycloakException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public KeycloakException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public KeycloakException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public KeycloakException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
