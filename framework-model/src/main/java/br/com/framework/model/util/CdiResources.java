package br.com.framework.model.util;


import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Esta classe é responsável por construir recursos CDI.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class CdiResources {

    @Produces
	public Logger produceLogger(InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}

}
