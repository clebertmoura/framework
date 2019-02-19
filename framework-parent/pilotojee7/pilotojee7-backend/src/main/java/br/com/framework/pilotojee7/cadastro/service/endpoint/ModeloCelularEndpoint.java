package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.FabricanteDao;
import br.com.framework.pilotojee7.cadastro.dao.ModeloCelularDao;
import br.com.framework.pilotojee7.cadastro.domain.Fabricante;
import br.com.framework.pilotojee7.cadastro.domain.ModeloCelular;
import br.com.framework.pilotojee7.cadastro.manager.ModeloCelularManager;
import br.com.framework.pilotojee7.cadastro.service.resource.ModeloCelularResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;

/**
 * Endpoint de {@link ModeloCelular}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/modeloCelular")
public class ModeloCelularEndpoint extends BaseEntityResourceEndpointImpl<Long, ModeloCelular, ModeloCelularResource, ModeloCelularDao, ModeloCelularManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private FabricanteDao fabricanteDao;
	
	@Override
	@Inject
	protected void setManager(ModeloCelularManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(ModeloCelularDao search) {
		super.setSearch(search);
	}

	@Override
	public ModeloCelular fromResource(ModeloCelular entity, ModeloCelularResource resource) {
		if (entity == null) {
			entity = new ModeloCelular();
		}
		// inicializa os atributos da entidade.
		entity.setNome(resource.getNome());
		// inicializa entidades relacionadas
		Fabricante fabricante = loadEntityRelation(entity.getFabricante(), resource.getFabricante(), fabricanteDao);
		entity.setFabricante(fabricante);
		return entity;
	}

	@Override
	protected ModeloCelularResource toResource(ModeloCelular entity) {
		return new ModeloCelularResource(entity);
	}
}