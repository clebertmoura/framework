package br.com.framework.codegen.frontend.entity;

import java.util.Arrays;

import br.com.framework.codegen.GeneratorProcessor;
import br.com.framework.codegen.dto.EntityMetamodel;
import br.com.framework.codegen.gen.BaseResourceGenerator;

/**
 * Gerador responsável pela geração do arquivo: .routes.js
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class EntitySearchGenerator extends BaseResourceGenerator{
	
	public static final String TEMPLATE = "frontend/entity/search.html.ftl";
	public static final String PATH = "app";
	public static final String SUFIX = "search";
	public static final String EXTENSION = ".html";
	
	/**
	 * @param generatorProcessor
	 * @param entityMetamodel
	 */
	public EntitySearchGenerator(GeneratorProcessor generatorProcessor, EntityMetamodel entityMetamodel) {
		super(generatorProcessor, entityMetamodel, TEMPLATE, PATH, SUFIX, EXTENSION);
		this.ignorePropertyNames = Arrays.asList("id", "versaoEntidade", "status", "createDate", "lastModifiedDate", "userLogin");
	}

	@Override
	protected String getGeneratorPath() {
		return PATH + "/" + getEntityNameCamelCase() + "/";
	}
	
	@Override
	public String getGeneratorFileName() {
		return getGeneratorPath().concat(getGeneratorFileNameSufix() + getGeneratorFileExtension());
	}
}
