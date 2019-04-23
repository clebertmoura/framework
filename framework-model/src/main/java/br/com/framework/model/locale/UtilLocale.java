/**
 * 
 */
package br.com.framework.model.locale;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.inject.Vetoed;

/**
 * @author ctm
 *
 */
@Vetoed
public class UtilLocale implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Locale locale;

	/**
	 * 
	 */
	public UtilLocale(Locale locale) {
		this.locale = locale;
	}

	public UtilLocale() {
		super();
	}

	/**
	 * Retorna o {@link Locale}
	 * 
	 * @return
	 */
	public Locale getLocale() {
		return locale;
	}

}
