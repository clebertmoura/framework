package br.com.framework.codegen.frontend.entity;

import br.com.framework.codegen.GeneratorProcessor;
import br.com.framework.codegen.dto.EntityMetamodel;
import br.com.framework.codegen.gen.BaseResourceGenerator;

/**
 * Gerador responsável pela geração do arquivo: .service.js
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class EntityServiceGenerator extends BaseResourceGenerator{
	
	public static final String TEMPLATE = "frontend/entity/entity.service.js.ftl";
	public static final String PATH = "app";
	public static final String SUFIX = ".service";
	public static final String EXTENSION = ".js";
	
	/**
	 * @param generatorProcessor
	 * @param entityMetamodel
	 */
	public EntityServiceGenerator(GeneratorProcessor generatorProcessor, EntityMetamodel entityMetamodel) {
		super(generatorProcessor, entityMetamodel, TEMPLATE, PATH, SUFIX, EXTENSION);
	}

}
