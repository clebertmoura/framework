package br.com.framework.codegen.backend;

import java.util.Arrays;

import br.com.framework.codegen.GeneratorProcessor;
import br.com.framework.codegen.dto.EntityMetamodel;
import br.com.framework.codegen.gen.BaseSourceGenerator;

/**
 * Gerador responsável pela geração do Endpoint da entidade.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class EndpointGenerator extends BaseSourceGenerator{
	
	public static final String TEMPLATE = "backend/endpoint.ftl";
	public static final String PACKAGE = "service.endpoint";
	public static final String SUFIX = "Endpoint";
	
	/**
	 * @param generatorProcessor
	 * @param entityMetamodel
	 */
	public EndpointGenerator(GeneratorProcessor generatorProcessor, EntityMetamodel entityMetamodel) {
		super(generatorProcessor, entityMetamodel, TEMPLATE, PACKAGE, SUFIX);
		this.ignorePropertyNames = Arrays.asList("id", "versaoEntidade", "status", "createDate", "lastModifiedDate", "userLogin");
	}

}
