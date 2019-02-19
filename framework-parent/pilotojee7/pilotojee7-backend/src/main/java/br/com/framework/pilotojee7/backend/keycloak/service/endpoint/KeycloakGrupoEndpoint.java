package br.com.framework.pilotojee7.backend.keycloak.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.backend.keycloak.dao.KeycloakGrupoDao;
import br.com.framework.pilotojee7.backend.keycloak.dao.KeycloakUsuarioDao;
import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakGrupo;
import br.com.framework.pilotojee7.backend.keycloak.manager.KeycloakGrupoManager;
import br.com.framework.pilotojee7.backend.keycloak.service.resource.KeycloakGrupoResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;

/**
 * Endpoint de {@link KeycloakGrupo}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/keycloakGrupo")
public class KeycloakGrupoEndpoint extends BaseEntityResourceEndpointImpl<Long, KeycloakGrupo, KeycloakGrupoResource, KeycloakGrupoDao, KeycloakGrupoManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private KeycloakUsuarioDao keycloakUsuarioDao;
	@Inject
	private KeycloakUsuarioEndpoint keycloakUsuarioEndpoint;
	
	@Override
	@Inject
	protected void setManager(KeycloakGrupoManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(KeycloakGrupoDao search) {
		super.setSearch(search);
	}

	@Override
	public KeycloakGrupo fromResource(KeycloakGrupo entity, KeycloakGrupoResource resource) {
		if (entity == null) {
			entity = new KeycloakGrupo();
		}
		// inicializa os atributos da entidade.
		entity.setIdKeycloak(resource.getIdKeycloak());
		entity.setNome(resource.getNome());
		// inicializa entidades relacionadas
		loadEntityRelations(entity.getUsuarios(), resource.getUsuarios(), keycloakUsuarioDao);
		return entity;
	}

	@Override
	protected KeycloakGrupoResource toResource(KeycloakGrupo entity) {
		return new KeycloakGrupoResource(entity);
	}
}