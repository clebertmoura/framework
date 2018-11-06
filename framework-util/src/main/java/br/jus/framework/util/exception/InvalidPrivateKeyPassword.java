/**
 * 
 */
package br.jus.framework.util.exception;

import java.security.GeneralSecurityException;

/**
 * Exceção lancada quando a senha informada para a chave privada estiver inválida.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public class InvalidPrivateKeyPassword extends GeneralSecurityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public InvalidPrivateKeyPassword() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param msg
	 */
	public InvalidPrivateKeyPassword(String msg) {
		super(msg);
	}

	/**
	 * @param cause
	 */
	public InvalidPrivateKeyPassword(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidPrivateKeyPassword(String message, Throwable cause) {
		super(message, cause);
	}

}
