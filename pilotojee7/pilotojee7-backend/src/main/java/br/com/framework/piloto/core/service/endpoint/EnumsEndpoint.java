package br.com.framework.piloto.core.service.endpoint;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.reflections.Reflections;

import br.com.framework.service.impl.BaseResourceEndpointImpl;
import br.com.framework.service.impl.BaseResourceImpl;


/**
 * Endpoint que publica todos os Enums da aplicação.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Stateless
@Path("/v1/enums")
public class EnumsEndpoint extends BaseResourceEndpointImpl<BaseResourceImpl>{

	private static final String FRAMEWORK_PACKAGE = "br.com.framework.domain";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1173742094500363316L;
	
	/**
	 * Pacotes de enum a serem publicados pela API
	 */
	private static final String[] enumPackages = {
		FRAMEWORK_PACKAGE,
		EnumsEndpoint.class.getPackage().getName().substring(0, 
			EnumsEndpoint.class.getPackage().getName().indexOf(".core.service.endpoint"))
	};
	
	@SuppressWarnings("rawtypes")
	private static final Set<Class<? extends Enum>> enumClasses = new HashSet<>();
	
	/**
	 * Carrega as classes de Enum
	 */
	static {
		for (String enumPackage : enumPackages) {
			Reflections reflections = new Reflections(enumPackage);
			enumClasses.addAll(reflections.getSubTypesOf(Enum.class));
		}
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GET
	@Path("/{EnumType}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEnumValues(@PathParam("EnumType") String enumType) {
		Class<? extends Enum> classFound = null;
		for (Class<? extends Enum> class1 : enumClasses) {
			if(class1.getSimpleName().equals(enumType)) {
				classFound = class1;
				break;
			}
		}
		return Response.ok(createEnumResourceList(classFound)).build();
	}

	
}

