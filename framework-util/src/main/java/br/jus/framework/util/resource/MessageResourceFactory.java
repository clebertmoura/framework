package br.jus.framework.util.resource;

import java.util.Locale;

/**
 * Fábrica de {@link MessageResource}
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public class MessageResourceFactory {

	/**
	 * 
	 */
	private MessageResourceFactory() {
		super();
	}
	
	/**
	 * @param bundle
	 * @param language
	 * @param cLoader
	 * @return
	 */
	public static MessageResource createMessageResource(String bundle, Locale locale,
			ClassLoader cLoader) {
		return new MessageResource(bundle, locale, cLoader);
	}
	
	/**
	 * @param bundle
	 * @return
	 */
	public static MessageResource createMessageResource(String bundle) {
		return new MessageResource(bundle);
	}
	
	/**
	 * @param bundle
	 * @param language
	 * @return
	 */
	public static MessageResource createMessageResource(String bundle, Locale locale) {
		return new MessageResource(bundle, locale, null);
	}
	
	/**
	 * @param bundle
	 * @param cLoader
	 * @return
	 */
	public static MessageResource createMessageResource(String bundle, ClassLoader cLoader) {
		return new MessageResource(bundle, null, cLoader);
	}

}
