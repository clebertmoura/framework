package br.com.framework.pilotojee7.backend.keycloak.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.backend.keycloak.dao.KeycloakGrupoDao;
import br.com.framework.pilotojee7.backend.keycloak.dao.KeycloakUsuarioDao;
import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakGrupo;
import br.com.framework.pilotojee7.backend.keycloak.domain.KeycloakUsuario;
import br.com.framework.pilotojee7.backend.keycloak.manager.KeycloakUsuarioManager;
import br.com.framework.pilotojee7.backend.keycloak.service.resource.KeycloakGrupoResource;
import br.com.framework.pilotojee7.backend.keycloak.service.resource.KeycloakUsuarioResource;
import br.com.framework.pilotojee7.cadastro.dao.ImagemDao;
import br.com.framework.pilotojee7.cadastro.domain.Imagem;
import br.com.framework.pilotojee7.cadastro.service.endpoint.ImagemEndpoint;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;
import br.com.framework.service.util.LoadRelatedEntityResource;

/**
 * Endpoint de {@link KeycloakUsuario}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/keycloakUsuario")
public class KeycloakUsuarioEndpoint extends BaseEntityResourceEndpointImpl<Long, KeycloakUsuario, KeycloakUsuarioResource, KeycloakUsuarioDao, KeycloakUsuarioManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private KeycloakGrupoDao keycloakGrupoDao;
	@Inject
	private KeycloakGrupoEndpoint keycloakGrupoEndpoint;
	@Inject
	private ImagemDao imagemDao;
	@Inject
	private ImagemEndpoint imagemEndpoint;
	
	@Override
	@Inject
	protected void setManager(KeycloakUsuarioManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(KeycloakUsuarioDao search) {
		super.setSearch(search);
	}

	@Override
	public KeycloakUsuario fromResource(KeycloakUsuario entity, KeycloakUsuarioResource resource) {
		if (entity == null) {
			entity = new KeycloakUsuario();
		}
		// inicializa os atributos da entidade.
		entity.setPrimeiroNome(resource.getPrimeiroNome());
		entity.setUltimoNome(resource.getUltimoNome());
		entity.setEmail(resource.getEmail());
		entity.setLogin(resource.getLogin());
		entity.setIdKeycloak(resource.getIdKeycloak());
		// inicializa entidades relacionadas
		loadEntityRelations(entity.getGrupos(), resource.getGrupos(), keycloakGrupoDao, new LoadRelatedEntityResource<KeycloakUsuario, KeycloakGrupo, KeycloakGrupoResource>(entity) {
			@Override
			public KeycloakGrupo loadRelatedEntityResource(KeycloakGrupo relatedEntity,
					KeycloakGrupoResource relatedResource) {
				relatedEntity = keycloakGrupoEndpoint.fromResource(relatedEntity, relatedResource);
				return relatedEntity;
			}
		});
		Imagem imagem = loadEntityRelation(entity.getImagem(), resource.getImagem(), imagemDao);
		entity.setImagem(imagem);
		return entity;
	}

	@Override
	protected KeycloakUsuarioResource toResource(KeycloakUsuario entity) {
		return new KeycloakUsuarioResource(entity);
	}
}