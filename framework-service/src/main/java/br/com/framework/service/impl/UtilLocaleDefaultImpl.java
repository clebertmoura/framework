package br.com.framework.service.impl;

import java.util.Locale;

import br.com.framework.service.api.UtilLocale;

public class UtilLocaleDefaultImpl implements UtilLocale {

	private Locale locale;

	public UtilLocaleDefaultImpl() {
		this.locale = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

}
