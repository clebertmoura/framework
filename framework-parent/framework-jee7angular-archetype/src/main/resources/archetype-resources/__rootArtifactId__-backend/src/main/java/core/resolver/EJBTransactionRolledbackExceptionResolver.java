#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJBTransactionRolledbackException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.exception.ExceptionUtils;

import br.com.framework.model.exception.ModelException;
import br.com.framework.service.api.Error;
import br.com.framework.service.util.UtilBuilder;
import br.com.framework.util.resource.MessageResource;
import br.com.framework.util.resource.MessageResourceFactory;

/**
 * Trata a exception {@link EJBTransactionRolledbackException}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Provider
public class EJBTransactionRolledbackExceptionResolver implements
        ExceptionMapper<EJBTransactionRolledbackException> {
	
	@Context 
	private HttpServletRequest request;
	
	private MessageResource messageResource;
	
	private void initMessageResource() {
		if (messageResource == null) {
			messageResource = MessageResourceFactory.createMessageResource("Mensagens", request.getLocale(), getClass().getClassLoader());
		}
	}
	
	@Override
	public Response toResponse(EJBTransactionRolledbackException exception) {
		initMessageResource();
		Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
		Response response = null;
		Throwable causedByException = ExceptionUtils.getRootCause(exception);
		if (causedByException instanceof ConstraintViolationException) {
			httpStatus = Response.Status.BAD_REQUEST;
			Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) causedByException).getConstraintViolations();
	        List<Error> errors = new ArrayList<Error>();
	        for (ConstraintViolation<?> violation : violations) {
	        	errors.add(UtilBuilder.buildError(violation.getPropertyPath().toString(), violation.getMessage()));
	        }
	        response = Response.status(httpStatus).entity(errors).build();
		} else if (causedByException instanceof ModelException) {
			httpStatus = Response.Status.BAD_REQUEST;
			ModelException me = (ModelException) causedByException;
			Map<String, Object[]> mensagensMap = me.getMensagensMap();
			List<Error> errors = new ArrayList<Error>();
			for (String key : mensagensMap.keySet()) {
				Object[] params = mensagensMap.get(key);
				String msg = messageResource.get(key, params);
				errors.add(UtilBuilder.buildError(key, msg));
			}
	        response = Response.status(httpStatus).entity(errors).build();
		} else {
			response = Response.status(httpStatus).entity(causedByException.getMessage()).build();
		}
		return response;
	}
 
}