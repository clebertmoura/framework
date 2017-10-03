package br.com.framework.pilotojee7.relatorio.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.relatorio.dao.CategoriaDao;
import br.com.framework.pilotojee7.relatorio.domain.Categoria;
import br.com.framework.pilotojee7.relatorio.manager.CategoriaManager;
import br.com.framework.pilotojee7.relatorio.service.resource.CategoriaResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;


/**
 * Endpoint de {@link Categoria}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/categoria")
public class CategoriaEndpoint extends BaseEntityResourceEndpointImpl<Long, Categoria, CategoriaResource, CategoriaDao, CategoriaManager>{

	private static final long serialVersionUID = -1L;
	
	
	@Override
	@Inject
	protected void setManager(CategoriaManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(CategoriaDao search) {
		super.setSearch(search);
	}

	@Override
	public Categoria fromResource(Categoria entity, CategoriaResource resource) {
		if (entity == null) {
			entity = new Categoria();
		}
		// inicializa os atributos da entidade.
		entity.setNome(resource.getNome());
		entity.setPermissao(resource.getPermissao());
		// inicializa entidades relacionadas
		return entity;
	}

	@Override
	protected CategoriaResource toResource(Categoria entity) {
		return new CategoriaResource(entity);
	}
}