/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-javaee-jpa:src/main/java/context/LocaleHolder.p.vm.java
 */
package br.jus.framework.piloto.core.util;

import java.util.Locale;

public class LocaleHolder {
	
	private static final ThreadLocal<Locale> currentLocaleHolder = new ThreadLocal<Locale>();

	public static Locale getLocale() {
		Locale locale = currentLocaleHolder.get();

		return locale != null ? locale : Locale.getDefault();
	}

	public static void setLocale(Locale locale) {
		currentLocaleHolder.set(locale);
	}
	
}