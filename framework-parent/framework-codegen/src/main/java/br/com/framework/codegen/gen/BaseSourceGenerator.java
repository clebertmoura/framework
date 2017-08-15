package br.com.framework.codegen.gen;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import br.com.framework.codegen.GeneratorProcessor;
import br.com.framework.codegen.dto.EntityMetamodel;
import br.com.framework.codegen.dto.EntityPropertyMetamodel;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Gerador base.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public abstract class BaseSourceGenerator {
	
	protected GeneratorProcessor generatorProcessor;
	
	protected EntityMetamodel entityMetamodel;
	
	private String templateFileName;
	
	/**
	 * Pacote base da aplicação.
	 */
	private String basePackage;
	/**
	 * Nome do módulo.
	 */
	private String modulePackage;
	/**
	 * Nome da entidade.
	 */
	private String entityName;
	/**
	 * Pacote referente ao gerador.
	 */
	private String generatorPackage;
	/**
	 * Sufixo adicionado ao nome da classe referente ao gerador.
	 */
	private String generatorClassNameSufix;
	
	protected List<String> ignorePropertyNames = new ArrayList<String>();
	
	/**
	 * @param generatorProcessor
	 * @param entityMetamodel
	 * @param templateFileName
	 * @param generatorPackage
	 * @param generatorClassNameSufix
	 */
	public BaseSourceGenerator(GeneratorProcessor generatorProcessor, EntityMetamodel entityMetamodel,
			String templateFileName, String generatorPackage, String generatorClassNameSufix) {
		this.generatorProcessor = generatorProcessor;
		this.entityMetamodel = entityMetamodel;
		this.templateFileName = templateFileName;
		this.generatorPackage = generatorPackage;
		this.generatorClassNameSufix = generatorClassNameSufix;
		String entityPackage = getEntityElement().getEnclosingElement().toString();
		this.entityName = getEntityElement().getSimpleName().toString();
		this.modulePackage = entityPackage.substring(0, entityPackage.lastIndexOf("."));
		this.basePackage = getModulePackage().substring(0, getModulePackage().lastIndexOf("."));
	}
	
	/**
	 * Retorna o mapa com os parâmetros que serão passados para o template.
	 * Pode ser sobrecarregado na classe concreta para adição de novos parâmetros.
	 * 
	 * @return Map<String, Object>
	 */
	protected Map<String, Object> getTemplatePropertiesMap() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("entityName", getEntityName());
		map.put("entityNameCamelCase", getEntityName().substring(0, 1)
				.toLowerCase().concat(getEntityName().substring(1)));
		map.put("modulePackage", getModulePackage());
		map.put("basePackage", getBasePackage());
		map.put("entityMetamodel", getEntityMetamodel());
		//map.put("isBaseEntityAudited", getEntityMetamodel().isBaseEntityAudited());
		//map.put("isBaseEntity", getEntityMetamodel().isBaseEntity());
		map.put("entityProperties", getFiltererdEntityPropertyMetamodel());
		map.put("entityImportSet", getEntityMetamodel().getImportSet());
		return map;
	}
	
	/**
	 * @return
	 */
	protected List<EntityPropertyMetamodel> getFiltererdEntityPropertyMetamodel() {
		List<EntityPropertyMetamodel> result = new ArrayList<EntityPropertyMetamodel>();
		for (EntityPropertyMetamodel entityPropertyMetamodel : getEntityMetamodel().getProperties()) {
			if (this.ignorePropertyNames.contains(entityPropertyMetamodel.getPropertyNameCamelCase())) {
				continue;
			}
			result.add(entityPropertyMetamodel);
		}
		return result;
	}
	
	/**
	 * @param processingEnv
	 */
	public void generateFile(ProcessingEnvironment processingEnv) {
		try {
			JavaFileObject jfo = processingEnv.getFiler().createSourceFile(getGeneratorClassQualifiedName(), getEntityElement());
			Writer writer = jfo.openWriter();
			Template template = getGeneratorProcessor().getConfiguration().getTemplate(getTemplateFileName());
			Map<String, Object> propsMap = getTemplatePropertiesMap();
			template.process(propsMap, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	protected GeneratorProcessor getGeneratorProcessor() {
		return generatorProcessor;
	}

	protected String getTemplateFileName() {
		return templateFileName;
	}

	protected EntityMetamodel getEntityMetamodel() {
		return entityMetamodel;
	}

	protected TypeElement getEntityElement() {
		return entityMetamodel.getEntityElement();
	}

	protected String getBasePackage() {
		return basePackage;
	}

	protected String getModulePackage() {
		return modulePackage;
	}

	protected String getEntityName() {
		return entityName;
	}

	protected String getGeneratorPackage() {
		return generatorPackage;
	}
	
	public String getGeneratorPackageQualifiedName() {
		return getModulePackage().concat("." + getGeneratorPackage());
	}

	protected String getGeneratorClassNameSufix() {
		return generatorClassNameSufix;
	}

	public String getGeneratorClassQualifiedName() {
		return getGeneratorPackageQualifiedName().concat("." + getEntityName() + getGeneratorClassNameSufix());
	}
	
}
