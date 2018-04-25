package br.com.framework.pilotojee7.relatorio.service.endpoint;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.stream.JsonParsingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.framework.pilotojee7.core.enums.SimNao;
import br.com.framework.pilotojee7.relatorio.dao.FiltroDao;
import br.com.framework.pilotojee7.relatorio.domain.Filtro;
import br.com.framework.pilotojee7.relatorio.manager.FiltroManager;
import br.com.framework.pilotojee7.relatorio.service.resource.FiltroResource;
import br.com.framework.service.api.EnumResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;
import br.com.framework.service.util.UtilBuilder;

/**
 * Endpoint de {@link Filtro}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/filtro")
public class FiltroEndpoint extends BaseEntityResourceEndpointImpl<Long, Filtro, FiltroResource, FiltroDao, FiltroManager>{

	private static final long serialVersionUID = -1L;
	
	@Override
	@Inject
	protected void setManager(FiltroManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(FiltroDao search) {
		super.setSearch(search);
	}

	@Override
	public Filtro fromResource(Filtro entity, FiltroResource resource) {
		if (entity == null) {
			entity = new Filtro();
		}
		// inicializa os atributos da entidade.
		entity.setNome(resource.getNome());
		entity.setDescricao(resource.getDescricao());
		entity.setDados(resource.getDados());
		entity.setTipoFiltro(resource.getTipoFiltro());
		entity.setFlagSql(resource.getFlagSql());
		// inicializa entidades relacionadas
		
		return entity;
	}

	@Override
	protected FiltroResource toResource(Filtro entity) {
		return new FiltroResource(entity);
	}
	
	/**
	 * @param sql
	 * @return
	 */
	@POST
	@Path("/getFiltroItens")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getFiltroItens(FiltroResource filtroResource) {
		SimNao flagSql = filtroResource.getFlagSql();
		String dados = filtroResource.getDados();
		if (flagSql.equals(SimNao.S)) {
			List<Object[]> result = null;
			try {
				result = getSearch().executeQuery(dados);
				List<EnumResource> resources = new ArrayList<EnumResource>();
				for (Object[] objects : result) {
					EnumResource enumResource = UtilBuilder.buildEnumResource(objects[0].toString(), objects[1].toString());
					resources.add(enumResource);
				}
				return Response.ok(resources).build();
			} catch (EJBException e) {
				logger.error("Erro ao executar query de filtro", e);
				return Response.status(Status.BAD_REQUEST).build();
			}
		} else {
			try {
				JsonArray object = Json.createReader(new StringReader(dados)).readArray();
				return Response.ok(object).build();
			} catch (JsonParsingException e) {
				return Response.status(Status.BAD_REQUEST).build();
			}
		}
		
	}
	
	@GET
	@Path("/getFiltroItensById/{id:[0-9][0-9]*}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getFiltroItensById(@PathParam("id") Long idFiltro) {
		Filtro filtro = getSearch().findById(idFiltro).getUniqueResult();
		if (filtro != null) {
			SimNao flagSql = filtro.getFlagSql();
			String dados = filtro.getDados();
			if (flagSql.equals(SimNao.S)) {
				List<Object[]> result = null;
				try {
					result = getSearch().executeQuery(dados);
					List<EnumResource> resources = new ArrayList<EnumResource>();
					for (Object[] objects : result) {
						EnumResource enumResource = UtilBuilder.buildEnumResource(objects[0].toString(), objects[1].toString());
						resources.add(enumResource);
					}
					return Response.ok(resources).build();
				} catch (EJBException e) {
					logger.error("Erro ao executar query de filtro", e);
					return Response.status(Status.BAD_REQUEST).build();
				}
			} else {
				try {
					JsonArray object = Json.createReader(new StringReader(dados)).readArray();
					return Response.ok(object).build();
				} catch (JsonParsingException e) {
					return Response.status(Status.BAD_REQUEST).build();
				}
			}
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	
}