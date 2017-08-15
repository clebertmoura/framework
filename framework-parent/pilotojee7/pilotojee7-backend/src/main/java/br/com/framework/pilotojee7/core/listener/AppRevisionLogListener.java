package br.com.framework.pilotojee7.core.listener;


import javax.naming.NamingException;

import org.hibernate.envers.RevisionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private static final Logger LOG = LoggerFactory.getLogger(AppRevisionLogListener.class.getName());
	
	@Override
	public void newRevision(Object revisionEntity) {
		if (revisionEntity instanceof RevisionLogEntity) {
			RevisionLogEntity logEntity = (RevisionLogEntity) revisionEntity;
			try {
				LoggedUser loggedUser = LookupFactory.get().getComponentLookup().lookup(LoggedUser.class, false);
				logEntity.setLogin(loggedUser.getLoggedUser());
			} catch(NullPointerException e){
				LOG.error("Erro na configuração do AppRevisionLogListener", e);
			} catch(NamingException e){
				LOG.error("Erro no listener de RevisionLog", e);
			}
		} else {
			LOG.warn("RevisionEntity não é uma instancia de RevisionLogEntity");
		}
	}

}
