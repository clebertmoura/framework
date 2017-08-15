#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.hibernate.envers.RevisionListener;

import br.com.framework.domain.api.RevisionLogEntity;
import br.com.framework.model.manager.api.security.LoggedUser;
import br.com.framework.model.util.LookupFactory;

/**
 * Implementação do {@link RevisionListener} para atribuir o usuário que efetuou a modificação.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class AppRevisionLogListener implements RevisionListener {
	
	private static final Logger LOG = Logger.getLogger(AppRevisionLogListener.class.getName());
	
	@Override
	public void newRevision(Object revisionEntity) {
		RevisionLogEntity logEntity = (RevisionLogEntity) revisionEntity;
		try {
			LoggedUser loggedUser = LookupFactory.get().getComponentLookup().lookup(LoggedUser.class, false);
			logEntity.setLogin(loggedUser.getLoggedUser());
		} catch(NullPointerException e){
			LOG.log(Level.SEVERE, "Erro na configuração do AppRevisionLogListener");
		} catch(NamingException e){
			LOG.log(Level.SEVERE, "Erro no listener de RevisionLog", e);
		}
	}

}
