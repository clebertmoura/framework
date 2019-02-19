package br.com.framework.codegen.frontend.entity;

import br.com.framework.codegen.GeneratorProcessor;
import br.com.framework.codegen.dto.EntityMetamodel;
import br.com.framework.codegen.gen.BaseResourceGenerator;

/**
 * Gerador responsável pela geração do arquivo: .module.js
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class EntityModuleGenerator extends BaseResourceGenerator{
	
	public static final String TEMPLATE = "frontend/entity/entity.module.js.ftl";
	public static final String PATH = "app";
	public static final String SUFIX = ".module";
	public static final String EXTENSION = ".js";
	
	/**
	 * @param generatorProcessor
	 * @param entityMetamodel
	 */
	public EntityModuleGenerator(GeneratorProcessor generatorProcessor, EntityMetamodel entityMetamodel) {
		super(generatorProcessor, entityMetamodel, TEMPLATE, PATH, SUFIX, EXTENSION);
	}

}
