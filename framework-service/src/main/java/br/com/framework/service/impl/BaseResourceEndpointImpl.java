/**
 * 
 */
package br.com.framework.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.service.api.BaseResource;
import br.com.framework.service.api.BaseResourceEndpoint;
import br.com.framework.service.api.EnumResource;
import br.com.framework.service.util.UtilBuilder;
import br.com.framework.util.resource.MessageSource;

/**
 * Implementação abstrata base dos serviços REST para acesso aos resources.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public abstract class BaseResourceEndpointImpl<R extends BaseResource> implements BaseResourceEndpoint<R> {

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
	
	@Inject
	protected MessageSource messageSource;
	
	/**
	 * 
	 */
	public BaseResourceEndpointImpl() {
		super();
	}
	
	/**
	 * Retorna o {@link MessageSource}.
	 * @return
	 */
	protected MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * @param messageSource
	 */
	protected void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * Retorna o {@link Locale} da requisição e caso não exista, retorna o {@link Locale} default.
	 * @return
	 */
	protected Locale getLocale() {
		Locale locale = request.getLocale();
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return locale;
	}
	
	/**
	 * Cria uma lista de {@link EnumResource} baseado nos itens do Enum informado.
	 * 
	 * @param enumType
	 * @return
	 */
	protected <En extends Enum<En>> List<EnumResource> createEnumResourceList(Class<En> enumType) {
		List<EnumResource> resources = new ArrayList<>();
		MessageSource messageResource = getMessageSource();
		for (En item : enumType.getEnumConstants()) {
			String labelEnum = String.format("%s.%s", item.getClass().getSimpleName(), item.name());
			labelEnum = messageResource != null ? messageResource.getMessage(getLocale(), labelEnum) : labelEnum;
			EnumResource enumResource = UtilBuilder.buildEnumResource(item.name(), labelEnum);
			resources.add(enumResource);
		}
		return resources;
	}

}
