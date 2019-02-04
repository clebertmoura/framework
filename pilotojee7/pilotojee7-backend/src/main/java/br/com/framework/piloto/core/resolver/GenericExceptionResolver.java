package br.com.framework.piloto.core.resolver;

import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionResolver implements ExceptionMapper<Exception>{	
		
	private static final Logger LOGGER = Logger.getLogger(ConstraintViolationExceptionResolver.class.getName());
	
	@Override
	public Response toResponse(Exception exception) {
		Response.Status httpStatus = Response.Status.BAD_REQUEST;
		LOGGER.warning("Erro de violação de constraint na requisição.");
		return Response.status(httpStatus).build();
	}

}
