/**
 * 
 */
package br.jus.framework.piloto.core.manager;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import br.jus.framework.model.manager.api.security.LoggedUser;

/**
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
@Stateless
@Local(LoggedUser.class)
public class LoggedUserManager implements LoggedUser {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private SessionContext sessionContext;

	/**
	 * 
	 */
	public LoggedUserManager() {
	}

	/* (non-Javadoc)
	 * @see br.jus.framework.model.manager.api.LoggedUser#getLoggedUser()
	 */
	@Override
	public String getLoggedUser() {
		return sessionContext.getCallerPrincipal() != null ? sessionContext.getCallerPrincipal().getName() : null;
	}

}
