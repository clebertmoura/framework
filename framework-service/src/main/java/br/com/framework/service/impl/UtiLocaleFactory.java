package br.com.framework.service.impl;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.framework.service.api.UtilLocale;
import br.com.framework.service.qualifier.UtiLocaleQualifier;

@SessionScoped
public class UtiLocaleFactory implements Serializable {

	private static final long serialVersionUID = 1650274317694358350L;
	
	@Inject
	private HttpServletRequest request;
	
	@Produces
	@UtiLocaleQualifier(LocaleType.WEB_CONTEXT)
	public UtilLocale getUtilLocaleWebContext() {
		
		return new UtilLocaleWebContextImpl(request);
	}
	
	@Produces
	@UtiLocaleQualifier(LocaleType.SERVICE)
	public UtilLocale getUtilLocaleService() {
		
		return new UtilLocaleServiceImpl();
		
	}
		
	@Produces
	@UtiLocaleQualifier(LocaleType.DEFAULT)
	public UtilLocale getUtilLocaleDefault() {
		
		return new UtilLocaleDefaultImpl();
	}
	

}
