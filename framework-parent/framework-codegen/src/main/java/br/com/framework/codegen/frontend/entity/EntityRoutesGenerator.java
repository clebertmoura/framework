package br.com.framework.codegen.frontend.entity;

import br.com.framework.codegen.GeneratorProcessor;
import br.com.framework.codegen.dto.EntityMetamodel;
import br.com.framework.codegen.gen.BaseResourceGenerator;

/**
 * Gerador responsável pela geração do arquivo: .routes.js
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class EntityRoutesGenerator extends BaseResourceGenerator{
	
	public static final String TEMPLATE = "frontend/entity/entity.routes.js.ftl";
	public static final String PATH = "app";
	public static final String SUFIX = ".routes";
	public static final String EXTENSION = ".js";
	
	/**
	 * @param generatorProcessor
	 * @param entityMetamodel
	 */
	public EntityRoutesGenerator(GeneratorProcessor generatorProcessor, EntityMetamodel entityMetamodel) {
		super(generatorProcessor, entityMetamodel, TEMPLATE, PATH, SUFIX, EXTENSION);
	}

}
