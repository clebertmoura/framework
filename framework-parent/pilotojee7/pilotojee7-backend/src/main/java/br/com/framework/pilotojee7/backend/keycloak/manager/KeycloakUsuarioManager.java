package br.com.framework.pilotojee7.backend.keycloak.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import br.com.framework.model.exception.ModelException;
import br.com.framework.pilotojee7.backend.keycloak.dao.KeycloakUsuarioDao;
import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakUsuario;
import br.com.framework.pilotojee7.backend.keycloak.exception.KeycloakException;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade KeycloakUsuario.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class KeycloakUsuarioManager extends AppBaseManagerImpl<Long, KeycloakUsuario, KeycloakUsuarioDao> {

	@Inject
	private KeycloakAdminManager keycloakAdminManager;
	
	public KeycloakUsuarioManager() {
		super(KeycloakUsuario.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(KeycloakUsuarioDao searchable) {
		super.setSearch(searchable);
	}
	
	@Override
	public KeycloakUsuario insert(KeycloakUsuario entity)
			throws PersistenceException, ConstraintViolationException, ModelException, KeycloakException {
		Response response = keycloakAdminManager.createUser(entity);
		if (response.getStatus() == HttpStatus.SC_CREATED) {
			return super.insert(entity);
		} else {
			throw new KeycloakException();
		}
		
	}

}