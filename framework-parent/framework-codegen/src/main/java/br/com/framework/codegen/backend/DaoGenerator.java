package br.com.framework.codegen.backend;

import br.com.framework.codegen.GeneratorProcessor;
import br.com.framework.codegen.dto.EntityMetamodel;
import br.com.framework.codegen.gen.BaseSourceGenerator;

/**
 * Gerador responsável pela geração do Dao da entidade.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class DaoGenerator extends BaseSourceGenerator{
	
	public static final String TEMPLATE = "backend/dao.ftl";
	public static final String PACKAGE = "dao";
	public static final String SUFIX = "Dao";
	
	/**
	 * @param generatorProcessor
	 * @param entityMetamodel
	 */
	public DaoGenerator(GeneratorProcessor generatorProcessor, EntityMetamodel entityMetamodel) {
		super(generatorProcessor, entityMetamodel, TEMPLATE, PACKAGE, SUFIX);
	}

}
