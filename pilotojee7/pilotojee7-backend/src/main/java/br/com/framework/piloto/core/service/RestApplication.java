package br.com.framework.piloto.core.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("/api")
public class RestApplication extends Application {

	public RestApplication() {
		BeanConfig conf = new BeanConfig();
		conf.setTitle("API REST PJPE");
		conf.setDescription("Serviços disposibilizados pelo PJPE");
		conf.setVersion("1.0.0");
		conf.setHost("localhost:8080");
		conf.setSchemes(new String[] { "http" });
		conf.setResourcePackage("br.com.framework.piloto.service.endpoint");
		conf.setScan(true);
	}

}