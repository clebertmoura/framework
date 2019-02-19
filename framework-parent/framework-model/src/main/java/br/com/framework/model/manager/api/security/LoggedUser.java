package br.com.framework.model.manager.api.security;

import java.io.Serializable;

/**
 * Interface para acesso ao usuário logado.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public interface LoggedUser extends Serializable {

	/**
	 * @return
	 */
	String getLoggedUser();
	
}
