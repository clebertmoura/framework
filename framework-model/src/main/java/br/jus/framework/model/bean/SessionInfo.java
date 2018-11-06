package br.jus.framework.model.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Classe DTO utilizada para guardar informações na sessão do usuário e permitir o acesso dessas informações em todas as camadas.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
@SessionScoped @Named("sessionInfo")
public class SessionInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Locale locale;
	private Map<String, Object> attributes;

	public SessionInfo() {
		attributes = new HashMap<String, Object>();
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}
	
	public Object getAttribute(String key) {
		return attributes.get(key);
	}
	
	/**
	 * @param key
	 * @return
	 */
	public boolean hasAttribute(String key) {
		return getAttribute(key) != null;
	}

}
