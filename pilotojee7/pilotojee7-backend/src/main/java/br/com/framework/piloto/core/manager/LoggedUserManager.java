/**
 * 
 */
package br.com.framework.piloto.core.manager;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import br.com.framework.model.manager.api.security.LoggedUser;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
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

	/* (non-Javadoc)
	 * @see br.com.framework.model.manager.api.LoggedUser#getLoggedUser()
	 */
	@Override
	public String getLoggedUser() {
		return sessionContext.getCallerPrincipal() != null ? sessionContext.getCallerPrincipal().getName() : null;
	}

}
