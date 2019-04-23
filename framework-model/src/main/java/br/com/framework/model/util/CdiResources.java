package br.com.framework.model.util;


import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.model.locale.UtilLocale;


/**
 * Esta classe é responsável por construir recursos CDI.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class CdiResources {
	
	@Inject Instance<HttpServletRequest> request;

    @Produces
	public Logger produceLogger(InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}
    
    @Produces @Default
    @SessionScoped
    public UtilLocale produceLocale() {
    	if (!request.isAmbiguous() && !request.isUnsatisfied()) {
			HttpServletRequest httpServletRequest = request.get();
			return new UtilLocale(httpServletRequest.getLocale());
		} else {
			return new UtilLocale(new Locale(
					System.getProperty("user.language"), 
					System.getProperty("user.country")));
		}
    }

}
