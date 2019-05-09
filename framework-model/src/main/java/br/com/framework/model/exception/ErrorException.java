package br.com.framework.model.exception;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.ApplicationException;

import br.com.framework.model.error.api.Error;


/**
 * Classe de exceções de negocio.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@ApplicationException(rollback = true)
public class ErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected List<? extends Error> errors = new ArrayList<>();

	public ErrorException() {
	}
	
	public ErrorException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param error
	 * @param cause
	 */
	public <E extends Error> ErrorException(E error, Throwable cause) {
		super(error.getMessage(), cause);
		getErrors().add(error);
	}
	
	/**
	 * @param error
	 */
	public <E extends Error> ErrorException(E error) {
		this(error, null);
	}
	
	/**
	 * @param message
	 * @param errors
	 */
	public <E extends Error> ErrorException(String message, List<E> errors) {
		super(message);
		this.errors = errors;
	}
	
	/**
	 * @param errors
	 */
	public <E extends Error> ErrorException(List<E> errors) {
		this(null, errors);
	}
	
	/**
	 * @param error
	 */
	public <E extends Error> void addError(E error) {
		getErrors().add(error);
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Error> getErrors() {
		return (List<Error>) errors;
	}

}
