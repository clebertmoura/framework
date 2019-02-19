package br.com.framework.service.impl;

import java.util.Locale;

import br.com.framework.service.api.UtilLocale;

public class UtilLocaleServiceImpl implements UtilLocale {

	private Locale locale;
	
	public UtilLocaleServiceImpl() {
		
		this.locale = Locale.getDefault();
	}

	@Override
	public Locale getLocale() {

		return locale;
	}
}
