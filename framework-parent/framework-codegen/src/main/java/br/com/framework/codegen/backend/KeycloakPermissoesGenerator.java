package br.com.framework.codegen.backend;

import java.util.List;
import java.util.Map;

import br.com.framework.codegen.GeneratorProcessor;
import br.com.framework.codegen.dto.EntityMetamodel;
import br.com.framework.codegen.gen.BaseResourceGenerator;

/**
 * Gerador responsável pela geração do arquivo (JS) Controller da entidade.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class KeycloakPermissoesGenerator extends BaseResourceGenerator{
	
	public static final String TEMPLATE = "backend/keycloak_permissoes.ftl";
	public static final String PATH = "backend/scripts/keycloak/non-filtered/";
	public static final String SUFIX = "";
	public static final String EXTENSION = ".sh";
	
	private List<EntityMetamodel> entitiesMetamodel;
	
	/**
	 * @param generatorProcessor
	 * @param entitiesMetamodel
	 */
	public KeycloakPermissoesGenerator(GeneratorProcessor generatorProcessor, List<EntityMetamodel> entitiesMetamodel) {
		super(generatorProcessor, null, TEMPLATE, PATH, SUFIX, EXTENSION);
		this.entitiesMetamodel = entitiesMetamodel;
	}
	
	@Override
	protected String getGeneratorPath() {
		return PATH + "entity_permissoes";
	}
	
	@Override
	public String getGeneratorFileName() {
		return getGeneratorPath().concat(getGeneratorFileNameSufix() + getGeneratorFileExtension());
	}
	
	@Override
	protected Map<String, Object> getTemplatePropertiesMap() {
		Map<String, Object> templatePropertiesMap = super.getTemplatePropertiesMap();
		templatePropertiesMap.put("entitiesMetamodel", entitiesMetamodel);
		return templatePropertiesMap;
	}

}
