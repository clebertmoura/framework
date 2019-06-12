package br.com.framework.service.impl;

import java.io.Serializable;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.framework.service.api.UtilLocale;

public class UtiLocaleFactory implements Serializable {

	private static final long serialVersionUID = 1650274317694358350L;
	
	@Inject
	private Instance<HttpServletRequest> httpServletRequest;
	
	@Produces
	public UtilLocale produceUtilLocale() {
		UtilLocale utilLocale = null;
		if (!httpServletRequest.isUnsatisfied() && !httpServletRequest.isAmbiguous()) {
			utilLocale = new UtilLocaleWebContextImpl(httpServletRequest.get());
		} else {
			utilLocale =  new UtilLocaleDefaultImpl();
		}
		return utilLocale;
	}

}
