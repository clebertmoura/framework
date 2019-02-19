package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.CategoriaNoticiaDao;
import br.com.framework.pilotojee7.cadastro.domain.CategoriaNoticia;
import br.com.framework.pilotojee7.cadastro.manager.CategoriaNoticiaManager;
import br.com.framework.pilotojee7.cadastro.service.resource.CategoriaNoticiaResource;
import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;

/**
 * Endpoint de {@link CategoriaNoticia}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/categoriaNoticia")
public class CategoriaNoticiaEndpoint extends
		BaseEntityAuditedResourceEndpointImpl<Long, CategoriaNoticia, CategoriaNoticiaResource, CategoriaNoticiaDao, CategoriaNoticiaManager> {

	private static final long serialVersionUID = -1L;

	@Override
	@Inject
	protected void setManager(CategoriaNoticiaManager manager) {
		super.setManager(manager);
	}

	@Override
	@Inject
	protected void setSearch(CategoriaNoticiaDao search) {
		super.setSearch(search);
	}

	@Override
	public CategoriaNoticia fromResource(CategoriaNoticia entity, CategoriaNoticiaResource resource) {
		if (entity == null) {
			entity = new CategoriaNoticia();
		}
		// inicializa os atributos da entidade.
		entity.setNome(resource.getNome());
		// inicializa entidades relacionadas
		return entity;
	}

	@Override
	protected CategoriaNoticiaResource toResource(CategoriaNoticia entity) {
		return new CategoriaNoticiaResource(entity);
	}

}