package br.com.framework.codegen.backend;

import java.util.Arrays;

import br.com.framework.codegen.GeneratorProcessor;
import br.com.framework.codegen.dto.EntityMetamodel;
import br.com.framework.codegen.gen.BaseSourceGenerator;

/**
 * Gerador responsável pela geração do Resource da entidade.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class ResourceGenerator extends BaseSourceGenerator{
	
	public static final String TEMPLATE = "backend/resource.ftl";
	public static final String PACKAGE = "service.resource";
	public static final String SUFIX = "Resource";
	
	/**
	 * @param generatorProcessor
	 * @param entityMetamodel
	 */
	public ResourceGenerator(GeneratorProcessor generatorProcessor, EntityMetamodel entityMetamodel) {
		super(generatorProcessor, entityMetamodel, TEMPLATE, PACKAGE, SUFIX);
		this.ignorePropertyNames = Arrays.asList("id", "versaoEntidade", "status", "createDate", "lastModifiedDate", "userLogin");
	}

}
