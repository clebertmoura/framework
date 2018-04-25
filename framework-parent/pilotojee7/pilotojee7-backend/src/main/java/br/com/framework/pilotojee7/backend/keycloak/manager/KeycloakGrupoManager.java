package br.com.framework.pilotojee7.backend.keycloak.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.backend.keycloak.dao.KeycloakGrupoDao;
import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakGrupo;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade KeycloakGrupo.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class KeycloakGrupoManager extends AppBaseManagerImpl<Long, KeycloakGrupo, KeycloakGrupoDao> {

	public KeycloakGrupoManager() {
		super(KeycloakGrupo.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(KeycloakGrupoDao searchable) {
		super.setSearch(searchable);
	}

}