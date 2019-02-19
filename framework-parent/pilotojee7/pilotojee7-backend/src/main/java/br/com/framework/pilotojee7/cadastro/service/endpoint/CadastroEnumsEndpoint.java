package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

import br.com.framework.service.impl.BaseResourceEndpointImpl;
import br.com.framework.service.impl.BaseResourceImpl;

/**
 * Endpoint dos Enums do modulo de cadastro.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Stateless
@Path("/cadastro/enums")
public class CadastroEnumsEndpoint extends BaseResourceEndpointImpl<BaseResourceImpl>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1173742094500363316L;

	/*@GET
	@Path("/SimNao")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getSimNao() {
		return Response.ok(createEnumResourceList(SimNao.class)).build();
	}
	
	@GET
	@Path("/Genero")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getGenero() {
		return Response.ok(createEnumResourceList(Genero.class)).build();
	}
	
	@GET
	@Path("/TipoContato")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getTipoContato() {
		return Response.ok(createEnumResourceList(TipoContato.class)).build();
	}
	
	@GET
	@Path("/TipoPessoa")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getTipoPessoa() {
		return Response.ok(createEnumResourceList(TipoPessoa.class)).build();
	}
	
	@GET
	@Path("/UsoSimcard")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUsoSimcard() {
		return Response.ok(createEnumResourceList(UsoSimcard.class)).build();
	}
	
	@GET
	@Path("/DiaSemana")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getDiaSemana() {
		return Response.ok(createEnumResourceList(DiaSemana.class)).build();
	}*/
	
}
