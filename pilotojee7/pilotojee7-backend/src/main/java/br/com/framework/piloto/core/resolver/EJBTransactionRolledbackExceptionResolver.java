package br.com.framework.piloto.core.resolver;

import javax.ejb.EJBTransactionRolledbackException;
import javax.persistence.OptimisticLockException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.exception.ExceptionUtils;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.model.error.impl.ErrorBuilder;
import br.com.framework.model.exception.ModelException;

/**
 * Trata a exception {@link EJBTransactionRolledbackException}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Provider
public class EJBTransactionRolledbackExceptionResolver implements
        ExceptionMapper<EJBTransactionRolledbackException> {
	
	@Override
	public Response toResponse(EJBTransactionRolledbackException exception) {
		return handleEJBExceptionToResponse(exception);
	}
	
	/**
	 * @param e
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Response handleEJBExceptionToResponse(EJBTransactionRolledbackException e) {
		Response response;
		Throwable rootCause = ExceptionUtils.getRootCause(e);
		if (rootCause != null) {
			if (rootCause instanceof ConstraintViolationException) {
				response = exceptionToResponse((ConstraintViolationException)rootCause);
			} else if (rootCause instanceof OptimisticLockException) {
				BaseEntity entity = (BaseEntity) ((OptimisticLockException)rootCause).getEntity();
				response = Response.status(Status.CONFLICT).entity(entity.getId()).build();
			} else if (rootCause instanceof ModelException) {
				response = exceptionToResponse((ModelException)rootCause);
			} else {
				response = exceptionToResponse(rootCause);
			}
		} else {
			response = exceptionToResponse(e);
		}
		return response;
	}
	
	/**
	 * @param constraintViolationException
	 * @return
	 */
	protected Response exceptionToResponse(ConstraintViolationException constraintViolationException) {
		return Response.status(Status.BAD_REQUEST).entity(ErrorBuilder.buildFromConstraintViolationException(constraintViolationException)).build();
	}

	/**
	 * @param throwable
	 * @return
	 */
	protected Response exceptionToResponse(Throwable throwable) {
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ErrorBuilder.buildError(throwable.getMessage())).build();
	}

	/**
	 * @param modelException
	 * @return
	 */
	protected Response exceptionToResponse(ModelException modelException) {
		return Response.status(Status.BAD_REQUEST).entity(modelException.getErrors()).build();
	}
 
}