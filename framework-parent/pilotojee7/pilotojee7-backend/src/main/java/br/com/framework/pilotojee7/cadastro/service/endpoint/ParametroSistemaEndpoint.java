package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.framework.pilotojee7.cadastro.dao.ParametroSistemaDao;
import br.com.framework.pilotojee7.cadastro.domain.ParametroSistema;
import br.com.framework.pilotojee7.cadastro.manager.ParametroSistemaManager;
import br.com.framework.pilotojee7.cadastro.service.resource.ParametroSistemaResource;
import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.search.impl.Operator;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;

/**
 * Endpoint de {@link ParametroSistema}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/parametroSistema")
public class ParametroSistemaEndpoint extends
		BaseEntityResourceEndpointImpl<Long, ParametroSistema, ParametroSistemaResource, ParametroSistemaDao, ParametroSistemaManager> {

	private static final long serialVersionUID = -1L;

	@Override
	@Inject
	protected void setManager(ParametroSistemaManager manager) {
		super.setManager(manager);
	}

	@Override
	@Inject
	protected void setSearch(ParametroSistemaDao search) {
		super.setSearch(search);
	}

	@Override
	public ParametroSistema fromResource(ParametroSistema entity, ParametroSistemaResource resource) {
		if (entity == null) {
			entity = new ParametroSistema();
		}
		// inicializa os atributos da entidade.
		entity.setChave(resource.getChave());
		entity.setValor(resource.getValor());
		// inicializa entidades relacionadas
		return entity;
	}

	@Override
	protected ParametroSistemaResource toResource(ParametroSistema entity) {
		return new ParametroSistemaResource(entity);
	}
	
	@GET
	@Path("/consultarPorChave")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response consultarPorChave(@QueryParam("chave") String chave) {
		if (chave == null || chave.trim().equals("")) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		SearchUniqueResult<ParametroSistema> uniqueByField = getSearch().findUniqueByField("chave", Operator.EQ, chave);
		ParametroSistemaResource resource = toResource(uniqueByField.getUniqueResult());
		return Response.ok(resource).build();
	}
}