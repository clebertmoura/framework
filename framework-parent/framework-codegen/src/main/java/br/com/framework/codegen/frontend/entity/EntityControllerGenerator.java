package br.com.framework.codegen.frontend.entity;

import java.util.Arrays;

import br.com.framework.codegen.GeneratorProcessor;
import br.com.framework.codegen.dto.EntityMetamodel;
import br.com.framework.codegen.gen.BaseResourceGenerator;

/**
 * Gerador responsável pela geração do arquivo: .controller.js
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class EntityControllerGenerator extends BaseResourceGenerator{
	
	public static final String TEMPLATE = "frontend/entity/entity.controller.js.ftl";
	public static final String PATH = "app";
	public static final String SUFIX = ".controller";
	public static final String EXTENSION = ".js";
	
	/**
	 * @param generatorProcessor
	 * @param entityMetamodel
	 */
	public EntityControllerGenerator(GeneratorProcessor generatorProcessor, EntityMetamodel entityMetamodel) {
		super(generatorProcessor, entityMetamodel, TEMPLATE, PATH, SUFIX, EXTENSION);
		this.ignorePropertyNames = Arrays.asList("id", "versaoEntidade", "status", "createDate", "lastModifiedDate", "userLogin");
	}

}
