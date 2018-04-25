package br.com.framework.pilotojee7.backend.keycloak.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakGrupo;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade KeycloakGrupo.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class KeycloakGrupoDao extends AppBaseDaoImpl<Long, KeycloakGrupo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public KeycloakGrupoDao() {
		super(KeycloakGrupo.class);
	}
	
}
