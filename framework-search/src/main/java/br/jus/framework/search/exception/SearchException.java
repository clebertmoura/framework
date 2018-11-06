/**
 * 
 */
package br.jus.framework.search.exception;

import javax.ejb.ApplicationException;

/**
 * Exceção da pesquisa.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
@ApplicationException(rollback = false)
public class SearchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SearchException() {
	}

	/**
	 * @param message
	 */
	public SearchException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SearchException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SearchException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public SearchException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
