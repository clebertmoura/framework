package br.com.framework.model.util;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;


/**
 * Esta classe é responsável por construir recursos CDI.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class CdiResources {

    @Produces
	public Logger produceLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass()
				.getName());
	}

}
