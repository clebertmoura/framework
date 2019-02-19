package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.TipoLogradouroDao;
import br.com.framework.pilotojee7.cadastro.domain.TipoLogradouro;
import br.com.framework.pilotojee7.cadastro.manager.TipoLogradouroManager;
import br.com.framework.pilotojee7.cadastro.service.resource.TipoLogradouroResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;


/**
 * Endpoint de {@link TipoLogradouro}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/tipoLogradouro")
public class TipoLogradouroEndpoint extends BaseEntityResourceEndpointImpl<Long, TipoLogradouro, TipoLogradouroResource, TipoLogradouroDao, TipoLogradouroManager>{

	private static final long serialVersionUID = -1L;
	
	
	@Override
	@Inject
	protected void setManager(TipoLogradouroManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(TipoLogradouroDao search) {
		super.setSearch(search);
	}

	@Override
	public TipoLogradouro fromResource(TipoLogradouro entity, TipoLogradouroResource resource) {
		if (entity == null) {
			entity = new TipoLogradouro();
		}
		// inicializa os atributos da entidade.
		entity.setDescricao(resource.getDescricao());
		// inicializa entidades relacionadas
		return entity;
	}

	@Override
	protected TipoLogradouroResource toResource(TipoLogradouro entity) {
		return new TipoLogradouroResource(entity);
	}
}