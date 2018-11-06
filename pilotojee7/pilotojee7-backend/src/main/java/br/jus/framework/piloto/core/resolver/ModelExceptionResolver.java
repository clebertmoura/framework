package br.jus.framework.piloto.core.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.jus.framework.model.exception.ModelException;
import br.jus.framework.service.api.Error;
import br.jus.framework.service.util.UtilBuilder;
import br.jus.framework.util.resource.MessageResource;
import br.jus.framework.util.resource.MessageResourceFactory;

/**
 * Trata a exception {@link ModelException}
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
@Provider
public class ModelExceptionResolver implements
        ExceptionMapper<ModelException> {
	
	private static final Logger LOGGER = Logger.getLogger(ModelExceptionResolver.class.getName());
	
	@Context 
	private HttpServletRequest request;
	
	private MessageResource messageResource;
	
	private void initMessageResource() {
		if (messageResource == null) {
			messageResource = MessageResourceFactory.createMessageResource("Mensagens", request.getLocale(), getClass().getClassLoader());
		}
	}

	@Override
	public Response toResponse(ModelException exception) {
		initMessageResource();
		Response.Status httpStatus = Response.Status.BAD_REQUEST;
		LOGGER.severe("Erro de violação de regra de negócio na requisição.");
		Map<String, Object[]> mensagensMap = exception.getMensagensMap();
		List<Error> errors = new ArrayList<Error>();
		for (String key : mensagensMap.keySet()) {
			Object[] params = mensagensMap.get(key);
			String msg = messageResource.get(key, params);
			errors.add(UtilBuilder.buildError(key, msg));
		}
		return Response.status(httpStatus).entity(errors).build();
	}
 
}