#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.framework.service.api.Error;
import br.com.framework.service.util.UtilBuilder;

/**
 * Trata a exception {@link ConstraintViolationException}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Provider
public class ConstraintViolationExceptionResolver implements
        ExceptionMapper<ConstraintViolationException> {
	
	private static final Logger LOGGER = Logger.getLogger(ConstraintViolationExceptionResolver.class.getName());

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		Response.Status httpStatus = Response.Status.BAD_REQUEST;
		Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
		LOGGER.warning("Erro de violação de constraint na requisição.");
        List<Error> errors = new ArrayList<Error>();
        for (ConstraintViolation<?> violation : violations) {
        	errors.add(UtilBuilder.buildError(violation.getPropertyPath().toString(), violation.getMessage()));
        }
		return Response.status(httpStatus).entity(errors).build();
	}
 
}