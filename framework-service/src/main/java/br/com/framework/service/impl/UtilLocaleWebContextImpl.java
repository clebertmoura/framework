package br.com.framework.service.impl;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import br.com.framework.service.api.UtilLocale;

public class UtilLocaleWebContextImpl implements UtilLocale {

	private Locale locale;
	
	public UtilLocaleWebContextImpl(HttpServletRequest request) {
	
		if(request != null)
			this.locale = request.getLocale();
		else
			this.locale = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
		
	}
	
	
	@Override
	public Locale getLocale() {
		return locale;
	}


}
