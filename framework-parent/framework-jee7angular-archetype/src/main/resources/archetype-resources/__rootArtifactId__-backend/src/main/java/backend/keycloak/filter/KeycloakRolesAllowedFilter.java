#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.backend.keycloak.filter;

import java.lang.reflect.Method;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptor;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.HttpRequest;
import org.keycloak.KeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ${package}.backend.monitor.MonitorEndpoint;

/**
 * Keycloak request filter.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Dependent
@Interceptor
@Provider
@ServerInterceptor
public class KeycloakRolesAllowedFilter implements javax.ws.rs.container.ContainerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakRolesAllowedFilter.class);

	@Context
	ResourceInfo resourceInfo;

	@Context
	private HttpRequest httpRequest;

	@Override
	public void filter(ContainerRequestContext requestContext) {
		Boolean hasClassAccess = null;
		Boolean hasMethodAccess = null;

		// Recupera as informações do usuário autenticado pelo Keycloak.
		KeycloakSecurityContext securityContext = (KeycloakSecurityContext) httpRequest.getAttribute(KeycloakSecurityContext.class.getName());
		Boolean hasNoAuthToken = (securityContext == null || securityContext.getTokenString() == null ? true : false);
		// System.out.println(String.format("[UsuarioEndpoint]: Usuario:'%s' token: '%s'", accessToken.getPreferredUsername(), securityContext.getTokenString()));

		// Verifica se existem permissões configuradas para a classe chamada.
		Class<?> resourceClass = resourceInfo.getResourceClass();
		if (resourceClass.isAnnotationPresent(RolesAllowed.class)) {
			String[] rolesAllowed = resourceClass.getAnnotation(RolesAllowed.class).value();
			hasClassAccess = false;
			if (securityContext != null) {
				for (String role : rolesAllowed) {
					if (securityContext.getToken().getRealmAccess().isUserInRole(role)) {
						hasClassAccess = true;
						break;
					}
				}
			}
		} else if(resourceClass.isAnnotationPresent(PermitAll.class)) {
			hasClassAccess = true;
		} else if(resourceClass.isAnnotationPresent(DenyAll.class)) {
			hasClassAccess = false;
		}
		
		// bypass para o endpoint de monitoramento
		if (resourceClass.equals(MonitorEndpoint.class)) {
			return;
		}
		
		// Verifica se existem permissões configuradas para o método da classe chamada.
		Method resourceMethod = resourceInfo.getResourceMethod();
		if (resourceMethod.isAnnotationPresent(RolesAllowed.class)) {
			String[] rolesAllowed = resourceMethod.getAnnotation(RolesAllowed.class).value();
			hasMethodAccess = false;
			if (securityContext != null) {
				for (String role : rolesAllowed) {
					if (securityContext.getToken().getRealmAccess().isUserInRole(role)) {
						hasMethodAccess = true;
						break;
					}
				}
			}
		} else if(resourceMethod.isAnnotationPresent(PermitAll.class)) {
			hasMethodAccess = true;
		} else if(resourceMethod.isAnnotationPresent(DenyAll.class)) {
			hasMethodAccess = false;
		}

		if ((hasClassAccess != null && !hasClassAccess && hasMethodAccess == null) || (hasMethodAccess != null && !hasMethodAccess) || hasNoAuthToken) {
			final ServerResponse serverResponse = new ServerResponse();
			serverResponse.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
			LOGGER.warn(serverResponse.toString());
			requestContext.abortWith(serverResponse);
		}

	}
}