package br.com.framework.codegen.backend;

import br.com.framework.codegen.GeneratorProcessor;
import br.com.framework.codegen.dto.EntityMetamodel;
import br.com.framework.codegen.gen.BaseSourceGenerator;

/**
 * Gerador responsável pela geração do Manager da entidade.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class ManagerGenerator extends BaseSourceGenerator{
	
	public static final String TEMPLATE = "backend/manager.ftl";
	public static final String PACKAGE = "manager";
	public static final String SUFIX = "Manager";
	
	/**
	 * @param generatorProcessor
	 * @param entityMetamodel
	 */
	public ManagerGenerator(GeneratorProcessor generatorProcessor, EntityMetamodel entityMetamodel) {
		super(generatorProcessor, entityMetamodel, TEMPLATE, PACKAGE, SUFIX);
	}

}
