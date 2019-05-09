package br.com.framework.util.resource;

import java.util.Locale;

/**
 * Interface que descreve um {@link MessageSource} 
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public interface MessageSource {
	
    void setBasenames(String... basenames);

    String getMessage(Locale locale, String key, Object... args);
    
}