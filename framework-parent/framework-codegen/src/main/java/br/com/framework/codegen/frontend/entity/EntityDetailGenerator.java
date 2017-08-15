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
public class EntityDetailGenerator extends BaseResourceGenerator{
	
	public static final String TEMPLATE = "frontend/entity/detail.html.ftl";
	public static final String PATH = "app";
	public static final String SUFIX = "detail";
	public static final String EXTENSION = ".html";
	
	/**
	 * @param generatorProcessor
	 * @param entityMetamodel
	 */
	public EntityDetailGenerator(GeneratorProcessor generatorProcessor, EntityMetamodel entityMetamodel) {
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
