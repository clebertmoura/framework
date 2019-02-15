package br.com.framework.model.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.ApplicationException;

import br.com.framework.model.error.impl.ErrorBusiness;
import br.com.framework.model.error.impl.ErrorDefault;


/**
 * Classe de exceções de negocio.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@ApplicationException(rollback = true)
public class ModelException extends ErrorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object[]>  mensagensMap = new HashMap<>();
	
	public ModelException() {
	}
	
	public ModelException(ErrorBusiness error, Throwable cause) {
		super(error, cause);
	}

	public ModelException(ErrorBusiness error) {
		super(error);
	}

	public ModelException(String message, List<ErrorBusiness> errors) {
		super(message, errors);
	}
	
	public ModelException(List<ErrorBusiness> errors) {
		super(errors);
	}

	public ModelException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param mensagens
	 * @deprecated utilize o nova estrutura de erros {@link ErrorDefault}
	 */
	@Deprecated
	public ModelException(Map<String, Object[]> mensagens) {
		super();
		this.mensagensMap = mensagens;
	}

	/**
	 * @return the mensagensMap
	 * @deprecated utilize o nova estrutura de erros {@link ErrorDefault}
	 */
	@Deprecated
	public Map<String, Object[]> getMensagensMap() {
		return mensagensMap;
	}
	
	/**
	 * @param chaveMensagem
	 * @param objects
	 * @deprecated utilize o nova estrutura de erros {@link ErrorDefault}
	 */
	@Deprecated
	public void addMensagem(String chaveMensagem, Object... objects) {
		getMensagensMap().put(chaveMensagem, objects);
	}
	
	/**
	 * @param chaveMensagem
	 * @deprecated utilize o nova estrutura de erros {@link ErrorDefault}
	 */
	@Deprecated
	public void addMensagem(String chaveMensagem) {
		getMensagensMap().put(chaveMensagem, new Object[0]);
	}

	/**
	 * Cria um mapa de mensagens vazio.
	 * @return
	 * @deprecated utilize o nova estrutura de erros {@link ErrorDefault}
	 */
	@Deprecated
	public static Map<String, Object[]> createMessagesMap() {
		return new HashMap<>();
	}

}
