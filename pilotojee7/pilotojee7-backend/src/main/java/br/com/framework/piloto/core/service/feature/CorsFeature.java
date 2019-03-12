package br.com.framework.piloto.core.service.feature;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

/**
 * Provider que registra o {@link CorsFilter} do Resteasy.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
/*@Provider*/
public class CorsFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
        context.register(corsFilter);
        return true;
    }  
}