package br.com.framework.codegen.gen;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

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
public abstract class BaseResourceGenerator {
	
	protected GeneratorProcessor generatorProcessor;
	
	protected EntityMetamodel entityMetamodel;
	
	private String templateFileName;
	
	/**
	 * Nome da entidade.
	 */
	private String entityName;
	/**
	 * Pacote referente ao gerador.
	 */
	private String generatorPath;
	/**
	 * Sufixo adicionado ao nome da classe referente ao gerador.
	 */
	private String generatorFileNameSufix;
	
	private String generatorFileExtension;
	
	protected List<String> ignorePropertyNames = new ArrayList<String>();
	
	/**
	 * @param generatorProcessor
	 * @param entityMetamodel
	 * @param templateFileName
	 * @param generatorPath
	 * @param generatorFileNameSufix
	 * @param generatorFileExtension
	 */
	public BaseResourceGenerator(GeneratorProcessor generatorProcessor, EntityMetamodel entityMetamodel,
			String templateFileName, String generatorPath, String generatorFileNameSufix, String generatorFileExtension) {
		this.generatorProcessor = generatorProcessor;
		this.entityMetamodel = entityMetamodel;
		this.templateFileName = templateFileName;
		this.generatorPath = generatorPath;
		this.generatorFileNameSufix = generatorFileNameSufix;
		this.generatorFileExtension = generatorFileExtension;
		if (getEntityElement() != null) {
			this.entityName = getEntityElement().getSimpleName().toString();
		}
	}
	
	/**
	 * Retorna o mapa com os parâmetros que serão passados para o template.
	 * Pode ser sobrecarregado na classe concreta para adição de novos parâmetros.
	 * 
	 * @return Map<String, Object>
	 */
	protected Map<String, Object> getTemplatePropertiesMap() {
		Map<String,Object> map = new HashMap<String, Object>();
		if (getEntityMetamodel() != null) {
			map.put("entityName", getEntityName());
			map.put("entityNameCamelCase", getEntityNameCamelCase());
			map.put("entityMetamodel", getEntityMetamodel());
			map.put("entityProperties", getFiltererdEntityPropertyMetamodel());
		}
		if (getGeneratorProcessor().getBundle().containsKey("moduleName")) {
			map.put("moduleName", getGeneratorProcessor().getBundle().getString("moduleName"));
		}
		return map;
	}

	/**
	 * @return
	 */
	protected String getEntityNameCamelCase() {
		return getEntityName().substring(0, 1).toLowerCase().concat(getEntityName().substring(1));
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
			FileObject fileObject = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT, "", getGeneratorFileName(), getEntityElement());
			Writer writer = fileObject.openWriter();
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
		if (getEntityMetamodel() != null) {
			return getEntityMetamodel().getEntityElement();
		}
		return null;
	}

	protected String getEntityName() {
		return entityName;
	}

	protected String getGeneratorPath() {
		return generatorPath;
	}
	
	protected String getGeneratorFileNameSufix() {
		return generatorFileNameSufix;
	}

	public String getGeneratorFileName() {
		return getGeneratorPath().concat("/" + getEntityNameCamelCase() + "/" + getEntityNameCamelCase() + getGeneratorFileNameSufix() + getGeneratorFileExtension());
	}

	public String getGeneratorFileExtension() {
		return generatorFileExtension;
	}

	public void setGeneratorFileExtension(String generatorFileExtension) {
		this.generatorFileExtension = generatorFileExtension;
	}
	
}
