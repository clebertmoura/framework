package br.com.framework.codegen;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.persistence.Entity;
import javax.tools.Diagnostic;

import br.com.framework.codegen.backend.DaoGenerator;
import br.com.framework.codegen.backend.DmlPermissoesGenerator;
import br.com.framework.codegen.backend.EndpointGenerator;
import br.com.framework.codegen.backend.KeycloakPermissoesGenerator;
import br.com.framework.codegen.backend.ManagerGenerator;
import br.com.framework.codegen.backend.ResourceGenerator;
import br.com.framework.codegen.dto.EntityMetamodel;
import br.com.framework.codegen.dto.EntityPropertyMetamodel;
import br.com.framework.codegen.frontend.ImportsGenerator;
import br.com.framework.codegen.frontend.MenusGenerator;
import br.com.framework.codegen.frontend.entity.EntityControllerGenerator;
import br.com.framework.codegen.frontend.entity.EntityDetailGenerator;
import br.com.framework.codegen.frontend.entity.EntityDetailIncGenerator;
import br.com.framework.codegen.frontend.entity.EntityFactoryGenerator;
import br.com.framework.codegen.frontend.entity.EntityModalControllerGenerator;
import br.com.framework.codegen.frontend.entity.EntityModalTplGenerator;
import br.com.framework.codegen.frontend.entity.EntityModuleGenerator;
import br.com.framework.codegen.frontend.entity.EntityRoutesGenerator;
import br.com.framework.codegen.frontend.entity.EntitySearchGenerator;
import br.com.framework.codegen.frontend.entity.EntityServiceGenerator;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

/**
 * Processador para anotação responsável por gerar os artefatos.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@SupportedAnnotationTypes("javax.persistence.Entity")
public class GeneratorProcessor extends AbstractProcessor {
	
	public static GeneratorProcessor INSTANCE = null;
	
	private Configuration configuration;
	
	//private Map<TypeElement, List<EntityPropertyMetamodel>> mapEntityProperties = new HashMap<TypeElement, List<EntityPropertyMetamodel>>();
	
	private ResourceBundle bundle;

	public GeneratorProcessor() {
		INSTANCE = this;
		configuration = new Configuration(Configuration.VERSION_2_3_24);
		configuration.setClassForTemplateLoading(GeneratorProcessor.class, "/templates");
		configuration.setDefaultEncoding("UTF-8");
		// Sets how errors will appear. During web page *development*
		// TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
		//configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		configuration.setLogTemplateExceptions(false);
		try {
			bundle = ResourceBundle.getBundle("codegen");
		} catch(MissingResourceException e) {}
	}
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (isGenerationEnabled()) {
			List<EntityMetamodel> entities = new ArrayList<EntityMetamodel>();
			for (Element elem : roundEnv.getElementsAnnotatedWith(Entity.class)) {
				TypeElement entityElement = (TypeElement) elem;
				EntityMetamodel entityMetamodel = buildEntityMetamodel(processingEnv, entityElement);
				
				if (isElementInGeneration(entityElement)) {
					entities.add(entityMetamodel);
					// se a geração de backend está ativada
					if (isBackendGenerationEnabled()) {
						if (isDaoGenerationEnabled(entityMetamodel)) {
							DaoGenerator daoGenerator = new DaoGenerator(this, entityMetamodel);
							daoGenerator.generateFile(processingEnv);
						}
						if (isManagerGenerationEnabled(entityMetamodel)) {
							ManagerGenerator managerGenerator = new ManagerGenerator(this, entityMetamodel);
							managerGenerator.generateFile(processingEnv);
						}
						if (isResourceGenerationEnabled(entityMetamodel)) {
							ResourceGenerator resourceGenerator = new ResourceGenerator(this, entityMetamodel);
							resourceGenerator.generateFile(processingEnv);
						}
						if (isEndpointGenerationEnabled(entityMetamodel)) {
							EndpointGenerator endpointGenerator = new EndpointGenerator(this, entityMetamodel);
							endpointGenerator.generateFile(processingEnv);
						}
					}
					// se a geração de frontend está ativada
					if (isFrontendGenerationEnabled(entityMetamodel)) {
						EntityFactoryGenerator entityFactoryGenerator = new EntityFactoryGenerator(this, entityMetamodel);
						entityFactoryGenerator.generateFile(processingEnv);
						EntityServiceGenerator entityServiceGenerator = new EntityServiceGenerator(this, entityMetamodel);
						entityServiceGenerator.generateFile(processingEnv);
						EntityControllerGenerator entityControllerGenerator = new EntityControllerGenerator(this, entityMetamodel);
						entityControllerGenerator.generateFile(processingEnv);
						EntityModalControllerGenerator entityModalControllerGenerator = new EntityModalControllerGenerator(this, entityMetamodel);
						entityModalControllerGenerator.generateFile(processingEnv);
						EntityModuleGenerator entityModuleGenerator = new EntityModuleGenerator(this, entityMetamodel);
						entityModuleGenerator.generateFile(processingEnv);
						EntityRoutesGenerator entityRoutesGenerator = new EntityRoutesGenerator(this, entityMetamodel);
						entityRoutesGenerator.generateFile(processingEnv);
						EntitySearchGenerator entitySearchGenerator = new EntitySearchGenerator(this, entityMetamodel);
						entitySearchGenerator.generateFile(processingEnv);
						EntityDetailGenerator entityDetailGenerator = new EntityDetailGenerator(this, entityMetamodel);
						entityDetailGenerator.generateFile(processingEnv);
						EntityDetailIncGenerator entityDetailIncGenerator = new EntityDetailIncGenerator(this, entityMetamodel);
						entityDetailIncGenerator.generateFile(processingEnv);
						EntityModalTplGenerator entityModalTplGenerator = new EntityModalTplGenerator(this, entityMetamodel);
						entityModalTplGenerator.generateFile(processingEnv);
					}
			        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, entityElement.toString());
				}
		    }
			if (!entities.isEmpty()) {
				ImportsGenerator importsGenerator = new ImportsGenerator(this, entities);
				importsGenerator.generateFile(processingEnv);
				MenusGenerator menusGenerator = new MenusGenerator(this, entities);
				menusGenerator.generateFile(processingEnv);
				if (isBackendGenerationEnabled()) {
					if (isScriptDMLGenerationEnabled()) {
						DmlPermissoesGenerator dmlPermsGenerator = new DmlPermissoesGenerator(this, entities);
						dmlPermsGenerator.generateFile(processingEnv);
					}
					if (isKeycloakPermsGenerationEnabled()) {
						KeycloakPermissoesGenerator kcPermsGenerator = new KeycloakPermissoesGenerator(this, entities);
						kcPermsGenerator.generateFile(processingEnv);
					}
				}
			}
		}
		return true;
	}
	
	protected boolean isGenerationEnabled() {
		boolean result = true;
		if (bundle != null) {
			if (bundle.containsKey("enabled")) {
				result = Boolean.valueOf(bundle.getString("enabled"));
			}
		}
		return result;
	}
	
	protected boolean isElementInGeneration(TypeElement entityElement) {
		boolean result = false;
		if (bundle != null) {
			if (bundle.containsKey("includes")) {
				String includesStr = bundle.getString("includes");
				if (!includesStr.trim().isEmpty()){
					String[] includes = includesStr.split("[,]");
					for (String include : includes) {
						include = include.trim();
						if (entityElement.toString().startsWith(include) || 
								entityElement.getSimpleName().toString().equals(include)) {
							result = true;
							break;
						}
					}
				}
			}
			if (bundle.containsKey("excludes")) {
				String excludesStr = bundle.getString("excludes");
				if (!excludesStr.trim().isEmpty()){
					String[] excludes = excludesStr.split("[,]");
					for (String exclude : excludes) {
						exclude = exclude.trim();
						if (entityElement.toString().startsWith(exclude) || 
								entityElement.getSimpleName().toString().equals(exclude)) {
							result = false;
							break;
						}
					}
				}
			}
		} else {
			result = true;
		}
		return result;
	}
	
	/**
	 * Indica se a geração de Backend está ativada.
	 * 
	 * @return
	 */
	protected boolean isBackendGenerationEnabled() {
		boolean enabled = true;
		if (bundle != null) {
			if (bundle.containsKey("BackendGen.Enabled")) {
				enabled = Boolean.valueOf(bundle.getString("BackendGen.Enabled"));
			}
		}
		return enabled;
	}
	
	/**
	 * Indica se a geração de DAO está ativada.
	 * 
	 * @param entityMetamodel
	 * @return
	 */
	protected boolean isDaoGenerationEnabled(EntityMetamodel entityMetamodel) {
		boolean enabled = true;
		if (bundle != null) {
			if (bundle.containsKey("BackendGen.Dao.Enabled")) {
				enabled = Boolean.valueOf(bundle.getString("BackendGen.Dao.Enabled"));
			}
		}
		return enabled;
	}
	
	/**
	 * Indica se a geração de Manager está ativada.
	 * 
	 * @param entityMetamodel
	 * @return
	 */
	protected boolean isManagerGenerationEnabled(EntityMetamodel entityMetamodel) {
		boolean enabled = true;
		if (bundle != null) {
			if (bundle.containsKey("BackendGen.Manager.Enabled")) {
				enabled = Boolean.valueOf(bundle.getString("BackendGen.Manager.Enabled"));
			}
		}
		return enabled;
	}
	
	/**
	 * Indica se a geração de Resource está ativada.
	 * 
	 * @param entityMetamodel
	 * @return
	 */
	protected boolean isResourceGenerationEnabled(EntityMetamodel entityMetamodel) {
		boolean enabled = true;
		if (bundle != null) {
			if (bundle.containsKey("BackendGen.Resource.Enabled")) {
				enabled = Boolean.valueOf(bundle.getString("BackendGen.Resource.Enabled"));
			}
		}
		return enabled;
	}
	
	/**
	 * Indica se a geração de Endpoint está ativada.
	 * 
	 * @param entityMetamodel
	 * @return
	 */
	protected boolean isEndpointGenerationEnabled(EntityMetamodel entityMetamodel) {
		boolean enabled = true;
		if (bundle != null) {
			if (bundle.containsKey("BackendGen.Endpoint.Enabled")) {
				enabled = Boolean.valueOf(bundle.getString("BackendGen.Endpoint.Enabled"));
			}
		}
		return enabled;
	}
	

	/**
	 * Indica se a geração de Endpoint está ativada.
	 * 
	 * @return
	 */
	protected boolean isScriptDMLGenerationEnabled() {
		boolean enabled = true;
		if (bundle != null) {
			if (bundle.containsKey("BackendGen.ScriptDML.Enabled")) {
				enabled = Boolean.valueOf(bundle.getString("BackendGen.ScriptDML.Enabled"));
			}
		}
		return enabled;
	}
	
	/**
	 * Indica se a geração de Endpoint está ativada.
	 * 
	 * @return
	 */
	protected boolean isKeycloakPermsGenerationEnabled() {
		boolean enabled = true;
		if (bundle != null) {
			if (bundle.containsKey("BackendGen.KeycloakPerms.Enabled")) {
				enabled = Boolean.valueOf(bundle.getString("BackendGen.KeycloakPerms.Enabled"));
			}
		}
		return enabled;
	}
	
	/**
	 * Indica se a geração de Frontend está ativada.
	 * 
	 * @param entityMetamodel
	 * @return
	 */
	protected boolean isFrontendGenerationEnabled(EntityMetamodel entityMetamodel) {
		boolean enabled = true;
		if (bundle != null) {
			if (bundle.containsKey("FrontendGen.Enabled")) {
				enabled = Boolean.valueOf(bundle.getString("FrontendGen.Enabled"));
			}
		}
		return enabled;
	}
	

	/**
	 * Controi o meta-modelo da entidade informada.
	 * 
	 * @param processingEnvironment
	 * @param entityElement
	 * @return
	 */
	public static EntityMetamodel buildEntityMetamodel(ProcessingEnvironment processingEnvironment, TypeElement entityElement) {
		EntityMetamodel entityMetamodel = new EntityMetamodel(entityElement);
		List<EntityPropertyMetamodel> entityProperties = entityMetamodel.getProperties();
		
		// varre todos os membros da classe
		List<? extends Element> allMembers = processingEnvironment.getElementUtils().getAllMembers(entityElement);
		EntityPropertyMetamodel entityPropertyId = null;
		for (Element element : allMembers) {
			String fieldElementName = element.getSimpleName().toString();
			if (element.getKind().equals(ElementKind.METHOD) &&
					(!element.getModifiers().contains(Modifier.STATIC) && element.getModifiers().contains(Modifier.PUBLIC)) &&
					(fieldElementName.startsWith("get") || fieldElementName.startsWith("is"))) {
				String propertyName = fieldElementName.startsWith("get") ? fieldElementName.substring(3) : fieldElementName.substring(2);
				ExecutableElement fieldGetter = (ExecutableElement) element;
				ExecutableElement fieldSetter = getFieldSetter(processingEnvironment, entityElement, propertyName);
				if (fieldSetter != null) {
					EntityPropertyMetamodel entityProperty = new EntityPropertyMetamodel(propertyName, entityMetamodel, fieldGetter, fieldSetter);
					entityProperties.add(entityProperty);
					if (!entityProperty.isPrimitiveAttribute()) {
						entityMetamodel.getImportSet().add(entityProperty.getTypeClassNameNoArgument());
						String typeArgumentClassName = entityProperty.getTypeArgumentClassName(); 
						if (typeArgumentClassName != null) {
							entityMetamodel.getImportSet().add(typeArgumentClassName);
						}
					}
					if (propertyName.equalsIgnoreCase("Id")) {
						entityPropertyId = entityProperty;
					}
					if (entityMetamodel.getPropertyLabel() == null &&
							entityProperty.isStringAttribute() && (propertyName.equals("Nome") || propertyName.equals("Descricao"))) {
						entityMetamodel.setPropertyLabel(entityProperty);
					}
				}
			}
		}
		if (entityMetamodel.getPropertyLabel() == null) {
			entityMetamodel.setPropertyLabel(entityPropertyId);
		}
		
		//mapEntityProperties.put(entityElement, entityProperties);
		
		return entityMetamodel;
	}
	
	/**
	 * Retorna o método Getter da propriedade informada.
	 * 
	 * @param entityElement
	 * @param propertyName
	 * @return
	 */
	public static Element getFieldGetter(ProcessingEnvironment processingEnvironment, TypeElement entityElement, String propertyName) {
		Element fieldGetter = null;
		List<? extends Element> allMembers = processingEnvironment.getElementUtils().getAllMembers(entityElement);
		for (Element element : allMembers) {
			if (element.getKind().equals(ElementKind.METHOD) 
					&& (element.getSimpleName().toString().equals("get" + propertyName) || element.getSimpleName().toString().equals("is" + propertyName))) {
				fieldGetter = element;
				break;
			}
		}
		return fieldGetter;
	}
	
	/**
	 * Retorna o método Setter da propriedade informada.
	 * 
	 * @param processingEnvironment
	 * @param entityElement
	 * @param propertyName
	 * @return
	 */
	public static ExecutableElement getFieldSetter(ProcessingEnvironment processingEnvironment, TypeElement entityElement, String propertyName) {
		ExecutableElement fieldSetter = null;
		List<? extends Element> allMembers = processingEnvironment.getElementUtils().getAllMembers(entityElement);
		for (Element element : allMembers) {
			if (element.getKind().equals(ElementKind.METHOD) 
					&& element.getSimpleName().toString().equals("set" + propertyName)) {
				fieldSetter = (ExecutableElement)element;
				break;
			}
		}
		return fieldSetter;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

//	public Map<TypeElement, List<EntityPropertyMetamodel>> getMapEntityProperties() {
//		return mapEntityProperties;
//	}
	
	public ProcessingEnvironment getProcessingEnv() {
		return processingEnv;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}
}
