package br.jus.framework.piloto.core.service.endpoint;

import java.util.Set;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.reflections.Reflections;

import br.jus.framework.service.impl.BaseResourceEndpointImpl;
import br.jus.framework.service.impl.BaseResourceImpl;


/**
 * Endpoint dos Enums do modulo de cadastro.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
@Stateless
@Path("/v1/enums")
public class EnumsEndpoint extends BaseResourceEndpointImpl<BaseResourceImpl>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1173742094500363316L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GET
	@Path("/{EnumType}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEnumValues(@PathParam("EnumType") String enumType) {
		Class<? extends Enum> classFound = null;
		Reflections reflections = new Reflections("br.jus.framework.piloto");
		Set<Class<? extends Enum>> allClasses = reflections.getSubTypesOf(Enum.class);
		for (Class<? extends Enum> class1 : allClasses) {
			if(class1.getSimpleName().equals(enumType)) {
				classFound = class1;
				break;
			}
		}
		return Response.ok(createEnumResourceList(classFound)).build();
	}
	
	
}

