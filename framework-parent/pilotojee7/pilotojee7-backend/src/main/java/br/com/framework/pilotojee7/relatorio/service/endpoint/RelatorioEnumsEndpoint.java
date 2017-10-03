package br.com.framework.pilotojee7.relatorio.service.endpoint;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.framework.pilotojee7.relatorio.enums.TipoFiltro;
import br.com.framework.service.impl.BaseResourceEndpointImpl;
import br.com.framework.service.impl.BaseResourceImpl;

/**
 * Endpoint dos Enums do modulo de relatorio.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Stateless
@Path("/relatorio/enums")
public class RelatorioEnumsEndpoint extends BaseResourceEndpointImpl<BaseResourceImpl>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1173742094500363316L;

	@GET
	@Path("/TipoFiltro")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getTipoFiltro() {
		return Response.ok(createEnumResourceList(TipoFiltro.class)).build();
	}

}
