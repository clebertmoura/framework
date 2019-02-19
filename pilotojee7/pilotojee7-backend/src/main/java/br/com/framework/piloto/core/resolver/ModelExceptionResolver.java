package br.com.framework.piloto.core.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.model.exception.ModelException;

/**
 * Trata a exception {@link ModelException}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Provider
public class ModelExceptionResolver implements ExceptionMapper<ModelException> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ModelExceptionResolver.class);
	
	@Context 
	private HttpServletRequest request;

	@Override
	public Response toResponse(ModelException exception) {
		LOGGER.error("Esta operação vioulou regra de negócio.", exception);
		return exceptionToResponse(exception);
	}
	
	/**
	 * @param modelException
	 * @return
	 */
	protected Response exceptionToResponse(ModelException modelException) {
		return Response.status(Status.BAD_REQUEST).entity(modelException.getErrors()).build();
	}
 
}