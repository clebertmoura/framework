package br.com.framework.pilotojee7.cadastro.service.endpoint;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.com.framework.pilotojee7.cadastro.dao.CategoriaNoticiaDao;
import br.com.framework.pilotojee7.cadastro.domain.CategoriaNoticia;
import br.com.framework.pilotojee7.cadastro.manager.CategoriaNoticiaManager;
import br.com.framework.pilotojee7.cadastro.service.resource.CategoriaNoticiaResource;
import br.com.framework.search.impl.Restriction;
import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;
import br.com.framework.service.impl.SynchronizeResponse;
import br.com.framework.service.impl.FindByRestrictionsRequest;
import br.com.trinity.qca.core.util.RestrictionUtil;

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

	@POST
	@Path("/sincronizarCategoriaNoticias")
	public Response sincronizarCategoriaNoticias(FindByRestrictionsRequest findByResRequest) {
		Long datetimeSync = null;
		Integer first = findByResRequest.getFirst();
		Integer max = findByResRequest.getMax();
		
		for (Restriction restriction : findByResRequest.getRestrictions()) {
			
			if (restriction.getField().equals("datetimeSync") && restriction.getValue() != null
					&& restriction.getValue() != "") {
				datetimeSync = Long.parseLong(findByResRequest.getRestrictions().get(0).getValue().toString());
			}
		}
		
		
		SynchronizeResponse<CategoriaNoticiaResource> sincronizarNoticias = getManager().sincronizarNoticias(dataUltimaSincronizacao, destaque, first, max, idsCategorias, titulo);

		return Response.ok(sincronizarNoticias).build();

	}
}