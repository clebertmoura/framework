package br.com.framework.piloto.core.resolver;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.model.error.impl.ErrorBuilder;

/**
 * Trata a exception {@link ConstraintViolationException}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Provider
public class ConstraintViolationExceptionResolver implements
        ExceptionMapper<ConstraintViolationException> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConstraintViolationExceptionResolver.class);

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		
		return exceptionToResponse(exception);
	}
	
	/**
	 * @param exception
	 * @return
	 */
	protected Response exceptionToResponse(ConstraintViolationException exception) {
		LOGGER.error("Esta operação violou validações do bean.", exception);
		return Response.status(Status.BAD_REQUEST).entity(ErrorBuilder.buildFromConstraintViolationException(exception)).build();
	}
 
}