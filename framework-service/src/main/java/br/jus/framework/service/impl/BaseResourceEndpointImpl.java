/**
 * 
 */
package br.jus.framework.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.jus.framework.model.exception.ModelException;
import br.jus.framework.service.api.BaseResource;
import br.jus.framework.service.api.BaseResourceEndpoint;
import br.jus.framework.service.api.EnumResource;
import br.jus.framework.service.api.Error;
import br.jus.framework.service.util.UtilBuilder;
import br.jus.framework.util.resource.MessageResource;
import br.jus.framework.util.resource.MessageResourceFactory;

/**
 * Implementação abstrata base dos serviços REST para acesso aos resources.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public abstract class BaseResourceEndpointImpl<R extends BaseResource> implements BaseResourceEndpoint<R> {

	public static final String DEFAULT_BUNDLE_MESSAGES = "Mensagens";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Context 
	protected HttpServletRequest request;
	@Context 
	protected HttpServletResponse response;
	@Context 
	protected HttpHeaders httpHeaders;
	
	private String bundleMessagesName;
	private MessageResource messageResource;
	
	/**
	 * 
	 */
	public BaseResourceEndpointImpl() {
		this(DEFAULT_BUNDLE_MESSAGES);
	}
	
	/**
	 * @param bundleMessagesName
	 */
	public BaseResourceEndpointImpl(String bundleMessagesName) {
		super();
		this.bundleMessagesName = bundleMessagesName;
	}

	/**
	 * Retorna o {@link MessageResource}.
	 * 
	 * @return
	 */
	protected MessageResource getMessageResource() {
		if (messageResource == null) {
			messageResource = MessageResourceFactory.createMessageResource(bundleMessagesName, 
					request.getLocale(), getClass().getClassLoader());
		}
		return messageResource;
	}
	
	/**
     * Cria uma {@link List} de Erros com as constrains violadas.
     * 
     * @param violations
     * @return
     */
    protected List<Error> createModelErrors(ModelException me) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Foram encontrados erros em validações de negócio! Total: " + me.getMensagensMap().size());
    	}
        List<Error> errors = new ArrayList<Error>();
        if (!me.getMensagensMap().isEmpty()) {
        	Map<String, Object[]> mensagensMap = me.getMensagensMap();
        	for (String key : mensagensMap.keySet()) {
    			Object[] objects = mensagensMap.get(key);
    			String msg = getMessageResource().get(key, objects);
    			errors.add(UtilBuilder.buildError(key, msg));
    		}
        } else {
        	errors.add(UtilBuilder.buildError("negocio", me.getMessage()));
        }
        return errors;
    }
    
    /**
     * Cria um {@link Error} com a {@link Exception} informada.
     * 
     * @param ex
     * @return
     */
    protected Error createGenericError(Exception ex) {
        return UtilBuilder.buildError(ex.getClass().getName(), ex.getMessage());
    }
    

	/**
	 * Cria uma lista de {@link EnumResource} baseado nos itens do Enum informado.
	 * 
	 * @param enumType
	 * @return
	 */
	protected <En extends Enum<En>> List<EnumResource> createEnumResourceList(Class<En> enumType) {
		List<EnumResource> resources = new ArrayList<EnumResource>();
		for (En item : enumType.getEnumConstants()) {
			EnumResource enumResource = UtilBuilder.buildEnumResource(item.name(), getMessageResource().get(
					String.format("%s.%s", item.getClass().getSimpleName(), item.name())));
			resources.add(enumResource);
		}
		return resources;
	}

}
