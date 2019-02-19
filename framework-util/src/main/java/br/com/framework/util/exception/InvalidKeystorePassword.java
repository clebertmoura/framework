/**
 * 
 */
package br.com.framework.util.exception;

import java.security.GeneralSecurityException;

/**
 * Exceção lancada quando a senha informada para o keystore estiver inválida.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class InvalidKeystorePassword extends GeneralSecurityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param msg
	 */
	public InvalidKeystorePassword(String msg) {
		super(msg);
	}

	/**
	 * @param cause
	 */
	public InvalidKeystorePassword(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidKeystorePassword(String message, Throwable cause) {
		super(message, cause);
	}

}
