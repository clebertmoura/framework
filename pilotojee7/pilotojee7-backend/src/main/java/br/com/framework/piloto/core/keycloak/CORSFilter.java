package br.com.framework.piloto.core.keycloak;
import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

/*@Provider*/
public class CORSFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		MultivaluedMap<String, Object> responseHeaders = responseContext.getHeaders();
		responseHeaders.putSingle("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, OPTIONS, TRACE, PATCH");
		responseHeaders.putSingle("Access-Control-Allow-Headers", "*");
		responseHeaders.putSingle("Access-Control-Allow-Origin", "*");
	}

}