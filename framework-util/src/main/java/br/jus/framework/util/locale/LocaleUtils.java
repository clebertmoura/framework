package br.jus.framework.util.locale;

import java.util.Locale;

/**
 * Classe utilitária de Locale.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public class LocaleUtils {

	public LocaleUtils() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Retorna o {@link Locale}
	 * 
	 * @param languageCountry
	 * @return
	 */
	public static Locale getLocale(String languageCountry) {
		Locale locale = Locale.getDefault();
		if (languageCountry != null) {
			String[] strings = languageCountry.split("[_]");
			if (strings.length == 2) {
				locale = new Locale(strings[0], strings[1]);
			} else {
				locale = new Locale(strings[0]);
			}
		}
		return locale;
	}

}
