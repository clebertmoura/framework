package br.com.framework.model.exception;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.ApplicationException;

import br.com.framework.model.log.impl.ErrorDefault;


/**
 * Classe de exceções de negocio.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@ApplicationException(rollback = true)
public class ModelException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object[]>  mensagensMap = new HashMap<>();
	
	private ErrorDefault errorDefault = null;
	

	public ModelException() {
	}

	public ModelException(ErrorDefault errorDefault) {
		super(errorDefault.getCause());
		this.errorDefault = errorDefault;
	}
	
	/**
	 * @param mensagensMap
	 * @param cause
	 */
	public ModelException(Map<String, Object[]> mensagensMap, Throwable cause) {
		super(cause);
		this.mensagensMap = mensagensMap;
	}

	/**
	 * @param mensagens
	 */
	public ModelException(Map<String, Object[]> mensagens) {
		this(mensagens, null);
	}

	/**
	 * @param cause
	 * @param chaveMensagem
	 * @param objects
	 */
	public ModelException(Throwable cause, String chaveMensagem, Object... objects) {
		super(cause);
		mensagensMap.put(chaveMensagem, objects);
	}

	/**
	 * @param cause
	 * @param chaveMensagem
	 */
	public ModelException(Throwable cause, String chaveMensagem) {
		super(cause);
		mensagensMap.put(chaveMensagem, new Object[0]);
	}
	
	/**
	 * @param chaveMensagem
	 * @param objects
	 */
	public ModelException(String chaveMensagem, Object... objects) {
		mensagensMap.put(chaveMensagem, objects);
	}
	
	/**
	 * @param chaveMensagem
	 */
	public ModelException(String chaveMensagem) {
		this(chaveMensagem, new Object[0]);
	}

	/**
	 * @return the mensagensMap
	 */
	public Map<String, Object[]> getMensagensMap() {
		return mensagensMap;
	}
	
	/**
	 * @param chaveMensagem
	 * @param objects
	 */
	public void addMensagem(String chaveMensagem, Object... objects) {
		getMensagensMap().put(chaveMensagem, objects);
	}
	
	/**
	 * @param chaveMensagem
	 */
	public void addMensagem(String chaveMensagem) {
		getMensagensMap().put(chaveMensagem, new Object[0]);
	}

	/**
	 * Cria um mapa de mensagens vazio.
	 * @return
	 */
	public static Map<String, Object[]> createMessagesMap() {
		return new HashMap<>();
	}
	
	public ErrorDefault getErroDefault() {
		return errorDefault;
	}

}
