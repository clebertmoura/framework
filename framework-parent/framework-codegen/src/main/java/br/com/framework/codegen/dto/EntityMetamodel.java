package br.com.framework.codegen.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;

import br.com.framework.codegen.annotations.Image;
import freemarker.template.utility.StringUtil;

/**
 * Meta-modelo para representar uma entidade.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class EntityMetamodel {

	private TypeElement	entityElement;
	private List<EntityPropertyMetamodel> properties = new ArrayList<EntityPropertyMetamodel>();
	private List<EntityPropertyMetamodel> propertiesImages;
	private List<EntityPropertyMetamodel> propertiesDaos;
	private List<EntityPropertyMetamodel> propertiesEnums;
	private Set<String> importSet = new HashSet<String>();
	private EntityPropertyMetamodel propertyLabel;
	private LinkedHashSet<String> enumModules;

	public EntityMetamodel() {
	}

	public EntityMetamodel(TypeElement entityElement) {
		super();
		this.entityElement = entityElement;
	}

	public EntityMetamodel(TypeElement entityElement, List<EntityPropertyMetamodel> properties) {
		super();
		this.entityElement = entityElement;
		this.properties = properties;
	}

	public TypeElement getEntityElement() {
		return entityElement;
	}

	public void setEntityElement(TypeElement entityElement) {
		this.entityElement = entityElement;
	}
	
	public String getSimpleName() {
		return getEntityElement().getSimpleName().toString();
	}

	public String getSimpleNameCamelCase() {
		String simpleName = getSimpleName();
		return simpleName.substring(0, 1).toLowerCase().concat(simpleName.substring(1));
	}
	
	public String getQualifiedName() {
		return getEntityElement().getQualifiedName().toString();
	}

	public List<EntityPropertyMetamodel> getProperties() {
		return properties;
	}

	public void setProperties(List<EntityPropertyMetamodel> properties) {
		this.properties = properties;
	}

	public Set<String> getImportSet() {
		return importSet;
	}

	public void setImportSet(Set<String> importList) {
		this.importSet = importList;
	}
	
	public List<EntityPropertyMetamodel> getPropertiesDaos() {
		if (propertiesDaos == null) {
			propertiesDaos = new ArrayList<>();
			List<String> propertiesTypeClasses = new ArrayList<>();
			for (EntityPropertyMetamodel epm : getProperties()) {
				String typeClassName = epm.getTypeClassName();
				if (!epm.isSimpleAttribute() && !propertiesTypeClasses.contains(typeClassName)) {
					propertiesTypeClasses.add(typeClassName);
					propertiesDaos.add(epm);
				}
			}
		}
		return propertiesDaos;
	}
	
	/**
	 * Retorn a lista de atributos anotados com @Image
	 * 
	 * @return
	 */
	public List<EntityPropertyMetamodel> getPropertiesImages() {
		if (propertiesImages == null) {
			propertiesImages = new ArrayList<>();
			List<String> propertiesNames = new ArrayList<>();
			for (EntityPropertyMetamodel epm : getProperties()) {
				Image image = epm.getGetter().getAnnotation(Image.class);
				if (image != null && !propertiesNames.contains(epm.getPropertyName())) {
					propertiesNames.add(epm.getPropertyName());
					propertiesImages.add(epm);
				}
			}
		}
		return propertiesImages;
	}
	
	public List<EntityPropertyMetamodel> getPropertiesEnums() {
		if (propertiesEnums == null) {
			propertiesEnums = new ArrayList<>();
			List<String> propertiesTypeClasses = new ArrayList<>();
			for (EntityPropertyMetamodel epm : getProperties()) {
				String typeClassName = epm.getTypeClassName();
				if (epm.isEnumerated() && !propertiesTypeClasses.contains(typeClassName)) {
					propertiesTypeClasses.add(typeClassName);
					propertiesEnums.add(epm);
				}
			}
		}
		return propertiesEnums;
	}
	
	public Set<String> getEnumModules() {
		if (enumModules == null) {
			enumModules = new LinkedHashSet<>();
			List<String> moduleNames = new ArrayList<>();
			for (EntityPropertyMetamodel epm : getPropertiesEnums()) {
				String typeClassName = epm.getTypeClassName();
				if (typeClassName.equals("br.com.framework.domain.api.BaseEntityAudited.Status")) {
					continue;
				}
				String modulePackage = typeClassName.substring(0, typeClassName.lastIndexOf(".enums."));
				String moduleName = modulePackage.substring(modulePackage.lastIndexOf(".") + 1);
				if (!moduleNames.contains(moduleName)) {
					moduleNames.add(moduleName);
					enumModules.add(StringUtil.capitalize(moduleName));
				}
			}
		}
		return enumModules;
	}

	public void setPropertiesDaos(List<EntityPropertyMetamodel> propertiesDaos) {
		this.propertiesDaos = propertiesDaos;
	}

	/**
	 * Retorna true caso o elemento informado implementa a interface BaseEntity. 
	 * 
	 * @param entityElement
	 * @return
	 */
	public static boolean isBaseEntity(TypeElement entityElement) {
		TypeMirror superclass = entityElement.getSuperclass();
		if (superclass instanceof NoType) {
			return false;
		} else {
			TypeElement superclassElem = (TypeElement)((DeclaredType)superclass).asElement();
			List<? extends TypeMirror> interfaces = superclassElem.getInterfaces();
			boolean found = false;
			for (TypeMirror iface : interfaces) {
				TypeElement ifaceElement = (TypeElement)((DeclaredType)iface).asElement();
				if (ifaceElement.getQualifiedName().toString().equals("br.com.framework.domain.api.BaseEntity")) {
					found = true;
					break;
				}
			}
			if (!found) {
				return isBaseEntity(superclassElem);
			} else {
				return found;
			}
		}
	}
	
	/**
	 * Retorna true caso esta entidade implemente a interface BaseEntity.
	 * 
	 * @return
	 */
	public boolean isBaseEntity() {
		return isBaseEntity(entityElement);
	}
	
	/**
	 * Retorna true caso o elemento informado implementa a interface BaseEntityAudited. 
	 * 
	 * @param entityElement
	 * @return
	 */
	public static boolean isBaseEntityAudited(TypeElement entityElement) {
		TypeMirror superclass = entityElement.getSuperclass();
		if (superclass instanceof NoType) {
			return false;
		} else {
			TypeElement superclassElem = (TypeElement)((DeclaredType)superclass).asElement();
			List<? extends TypeMirror> interfaces = superclassElem.getInterfaces();
			boolean found = false;
			for (TypeMirror iface : interfaces) {
				TypeElement ifaceElement = (TypeElement)((DeclaredType)iface).asElement();
				if (ifaceElement.getQualifiedName().toString().equals("br.com.framework.domain.api.BaseEntityAudited")) {
					found = true;
					break;
				}
			}
			if (!found) {
				return isBaseEntityAudited(superclassElem);
			} else {
				return found;
			}
		}
	}
	
	/**
	 * Retorna true caso a classe implemente a interface BaseEntityAudited. 
	 * @return
	 */
	public boolean isBaseEntityAudited() {
		return isBaseEntityAudited(entityElement);
	}

	public EntityPropertyMetamodel getPropertyLabel() {
		return propertyLabel;
	}

	public void setPropertyLabel(EntityPropertyMetamodel propertyLabel) {
		this.propertyLabel = propertyLabel;
	}
	
}
