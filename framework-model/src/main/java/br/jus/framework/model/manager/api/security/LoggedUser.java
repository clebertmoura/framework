package br.jus.framework.model.manager.api.security;

import java.io.Serializable;

/**
 * Interface para acesso ao usuário logado.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public interface LoggedUser extends Serializable {

	/**
	 * @return
	 */
	String getLoggedUser();
	
}
