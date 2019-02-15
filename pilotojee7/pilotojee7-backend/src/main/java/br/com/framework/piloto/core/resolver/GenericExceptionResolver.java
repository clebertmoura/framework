package br.com.framework.piloto.core.resolver;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Trata a exception {@link Exception}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Provider
public class GenericExceptionResolver implements ExceptionMapper<Exception>{	
		
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionResolver.class);
	
	@Override
	public Response toResponse(Exception exception) {
		LOGGER.error("Esta operação ocasionou um erro interno não tratado.", exception);
		return Response.serverError().build();
	}

}
