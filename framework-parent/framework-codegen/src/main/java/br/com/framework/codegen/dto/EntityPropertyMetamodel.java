package br.com.framework.codegen.dto;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import br.com.framework.codegen.GeneratorProcessor;
import br.com.framework.codegen.annotations.Image;
import freemarker.template.utility.StringUtil;

/**
 * Meta-modelo para representar uma propriedade de uma entidade.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class EntityPropertyMetamodel {

	private EntityMetamodel entityMetamodel;
	private String propertyName;
	private ExecutableElement getter;
	private ExecutableElement setter;

	public EntityPropertyMetamodel() {
	}

	public EntityPropertyMetamodel(String propertyName, EntityMetamodel entityMetamodel, ExecutableElement getter,
			ExecutableElement setter) {
		super();
		this.propertyName = propertyName;
		this.entityMetamodel = entityMetamodel;
		this.getter = getter;
		this.setter = setter;
	}
	
	public String getPropertyNameCamelCase() {
		return getPropertyName().substring(0, 1).toLowerCase().concat(getPropertyName().substring(1));
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public EntityMetamodel getEntityMetamodel() {
		return entityMetamodel;
	}

	public void setEntityMetamodel(EntityMetamodel entityMetamodel) {
		this.entityMetamodel = entityMetamodel;
	}

	public TypeElement getEntityElement() {
		return getEntityMetamodel().getEntityElement();
	}

	public ExecutableElement getGetter() {
		return getter;
	}

	public void setGetter(ExecutableElement getter) {
		this.getter = getter;
	}

	public ExecutableElement getSetter() {
		return setter;
	}

	public void setSetter(ExecutableElement setter) {
		this.setter = setter;
	}
	
	/**
	 * Retorna o nome qualificado do tipo.
	 * 
	 * @return
	 */
	public String getTypeClassName() {
		return getGetter().getReturnType().toString();
	}
	
	/**
	 * Retorna o nome do modulo ao qual esse tipo pertence.
	 * 
	 * @return
	 */
	public String getTypeClassModuleName() {
		String typeClassName = getTypeClassName();
		String modulePackage = typeClassName.substring(0, typeClassName.lastIndexOf(".enums."));
		String moduleName = modulePackage.substring(modulePackage.lastIndexOf(".") + 1);
		return moduleName;
	}
	
	/**
	 * Retorna o nome do modulo ao qual esse tipo pertence com primeira letra maiúscula.
	 * 
	 * @return
	 */
	public String getTypeClassModuleNameCapitalized() {
		return StringUtil.capitalize(getTypeClassModuleName());
	}
	
	/**
	 * Retorna o nome simples do tipo.
	 * 
	 * @return
	 */
	public String getTypeClassSimpleName() {
		String classSimpleName = null;
		try {
			if (isTypeArgumentPresent()) {
				DeclaredType returnType = (DeclaredType) getGetter().getReturnType();
				classSimpleName = ((TypeElement)returnType.asElement()).getSimpleName() + "<";
				List<? extends TypeMirror> typeArguments = returnType.getTypeArguments();
				for (int i = 0; i < typeArguments.size(); i++) {
					DeclaredType declaredType = (DeclaredType) typeArguments.get(i);
					classSimpleName += ((TypeElement)declaredType.asElement()).getSimpleName();
					if (i < typeArguments.size() - 1) {
						classSimpleName += ",";
					}
				}
				classSimpleName += ">";
			} else {
				classSimpleName = getTypeClassName().substring(getTypeClassName().lastIndexOf(".") + 1);
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		return classSimpleName;
	}
	
	public String getTypeClassSimpleNameCamelCase() {
		String typeClassSimpleName = getTypeClassSimpleName();
		return typeClassSimpleName.substring(0, 1).toLowerCase().concat(typeClassSimpleName.substring(1));
	}
	
	/**
	 * Retorna o nome qualificado do tipo, sem argumento genérico, caso exista.
	 * 
	 * @return
	 */
	public String getTypeClassNameNoArgument() {
		String string = null;
		try {
			TypeMirror returnType = getGetter().getReturnType();
			if (returnType.getKind().equals(TypeKind.DECLARED)) {
				string = ((DeclaredType)returnType).asElement().toString();
			} else if (returnType.getKind().equals(TypeKind.ARRAY)) {
				string = ((ArrayType)returnType).toString();
			} else {
				string = returnType.toString();
			}
		} catch(RuntimeException e) {
			throw new RuntimeException(e);
		}
		return string;
	}
	
	/**
	 * Retorna a string de import de Dao referente ao tipo do atributo.
	 * 
	 * @return
	 */
	public String getTypeClassNameNoArgumentAsDaoImport() {
		String typeClassNameNoArgument = getTypeClassNameNoArgument();
		return typeClassNameNoArgument.replace(".domain.", ".dao.").concat("Dao");
	}
	
	/**
	 * Retorna a string de import de Resource referente ao tipo do atributo.
	 * 
	 * @return
	 */
	public String getTypeClassNameNoArgumentAsResourceImport() {
		String typeClassNameNoArgument = getTypeClassNameNoArgument();
		return typeClassNameNoArgument.replace(".domain.", ".service.resource.").concat("Resource");
	}
	
	/**
	 * Retorna a string de import de Endpoint referente ao tipo do atributo.
	 * 
	 * @return
	 */
	public String getTypeClassNameNoArgumentAsEndpointImport() {
		String typeClassNameNoArgument = getTypeClassNameNoArgument();
		return typeClassNameNoArgument.replace(".domain.", ".service.endpoint.").concat("Endpoint");
	}
	
	/**
	 * Retorna o nome simples do tipo, sem argumento genérico, caso exista.
	 * 
	 * @return
	 */
	public String getTypeClassSimpleNameNoArgument() {
		return ((TypeElement)((DeclaredType)getGetter().getReturnType()).asElement()).getSimpleName().toString();
	}
	
	/**
	 * Indica se o campo é uma lista
	 * 
	 * @return
	 */
	public boolean isListAttribute() {
		return getTypeClassNameNoArgument().equals("java.util.List") || getTypeClassNameNoArgument().equals("java.util.ArrayList");
	}
	
	/**
	 * Indica se o campo é um BLOB.
	 * 
	 * @return
	 */
	public boolean isBlobAttribute() {
		return getTypeClassNameNoArgument().equals("byte[]");
	}
	
	/**
	 * Indica se o campo é um primitivo.
	 * @return
	 */
	public boolean isPrimitiveAttribute() {
		return getGetter().getReturnType().getKind().isPrimitive();
	}
	
	/**
	 * Indica se o campo é um array.
	 * @return
	 */
	public boolean isArrayAttribute() {
		return getGetter().getReturnType().getKind().equals(TypeKind.ARRAY);
	}
	
	/**
	 * Indica se o campo é Image
	 * 
	 * @return
	 */
	public boolean isImageAttribute() {
		Image image = getGetter().getAnnotation(Image.class);
		return image != null;
	}
	
	/**
	 * Indica se o campo Image deve conter o preview
	 * 
	 * @return
	 */
	public boolean isImageAttributePreview() {
		Image image = getGetter().getAnnotation(Image.class);
		return image != null && image.preview();
	}
	
	/**
	 * Indica se o campo Image deve fazer o recorte.
	 * 
	 * @return
	 */
	public boolean isImageAttributeCropped() {
		Image image = getGetter().getAnnotation(Image.class);
		return image != null && image.cropped();
	}
	
	/**
	 * Indica se o campo é NotNull
	 * 
	 * @return
	 */
	public boolean isNotNullAttribute() {
		Column column = getGetter().getAnnotation(Column.class);
		return column != null && !column.nullable();
	}
	
	/**
	 * Retorna o length definido para coluna.
	 * 
	 * @return
	 */
	public int getAttributeColumnLength() {
		Column column = getGetter().getAnnotation(Column.class);
		int length = 0;
		if (column != null) {
			length = column.length();
		}
		return length;
	}
	
	/**
	 * Indica se o campo é do tipo {@link LocalDate}
	 * @return
	 */
	public boolean isJavaTimeLocalAttibute() {
		return getTypeClassNameNoArgument().indexOf("java.time.") > -1;
	}
	
	/**
	 * Indica se o campo é {@link Temporal}
	 * 
	 * @return
	 */
	public boolean isTemporalAttribute() {
		Temporal temporal = getGetter().getAnnotation(Temporal.class);
		return temporal != null || isJavaTimeLocalAttibute();
	}
	
	/**
	 * Indica se o campo é {@link Temporal} DATE
	 * @return
	 */
	public boolean isTemporalDateAttribute() {
		Temporal temporal = getGetter().getAnnotation(Temporal.class);
		return (temporal != null && temporal.value().equals(javax.persistence.TemporalType.DATE)) || 
				(isJavaTimeLocalAttibute() && getTypeClassNameNoArgument().equals("java.time.LocalDate"));
	}
	
	/**
	 * Indica se o campo é {@link Temporal} TIME
	 * @return
	 */
	public boolean isTemporalTimeAttribute() {
		Temporal temporal = getGetter().getAnnotation(Temporal.class);
		return (temporal != null && temporal.value().equals(javax.persistence.TemporalType.TIME)) || 
				(isJavaTimeLocalAttibute() && getTypeClassNameNoArgument().equals("java.time.LocalTime"));
	}
	
	/**
	 * Indica se o campo é {@link Temporal} TIMESTAMP
	 * @return
	 */
	public boolean isTemporalTimestampAttribute() {
		Temporal temporal = getGetter().getAnnotation(Temporal.class);
		return (temporal != null && temporal.value().equals(javax.persistence.TemporalType.TIMESTAMP)) || 
				(isJavaTimeLocalAttibute() && getTypeClassNameNoArgument().equals("java.time.LocalDateTime"));
	}
	
	/**
	 * Indica se o campo é uma String
	 * 
	 * @return
	 */
	public boolean isStringAttribute() {
		return getTypeClassNameNoArgument().equals("java.lang.String");
	}
	
	/**
	 * Indica se o campo é do tipo Status da BaseEntityAudited
	 * 
	 * @return
	 */
	public boolean isBaseEntityAuditedStatusAttribute() {
		return getTypeClassNameNoArgument().equals("br.com.framework.domain.enums.Status");
	}
	
	/**
	 * Indica se este elemento possui argumento genérico. Exemplo: java.util.List<Usuario>
	 * 
	 * @return
	 */
	public boolean isTypeArgumentPresent() {
		boolean result = false;
		TypeMirror returnType = getGetter().getReturnType();
		if (returnType.getKind().equals(TypeKind.DECLARED)) {
			List<? extends TypeMirror> typeArguments = ((DeclaredType)returnType).getTypeArguments();
			result = typeArguments != null && typeArguments.size() > 0;
		}
		return result;
	}
	
	/**
	 * Retorna o tipo do argumento generico definido para esse elemento.
	 * 
	 * @return
	 */
	public String getTypeArgumentClassName() {
		String result = null;
		TypeMirror returnType = getGetter().getReturnType();
		if (returnType.getKind().equals(TypeKind.DECLARED)) {
			List<? extends TypeMirror> typeArguments = ((DeclaredType)returnType).getTypeArguments();
			if (typeArguments.size() > 0) {
				result = ((TypeElement)((DeclaredType)typeArguments.get(0)).asElement()).toString();
			}
		}
		return result;
	}
	
	/**
	 * Retorna a string de import de Dao referente ao tipo geerico passado como argumento para o atributo coleção.
	 * 
	 * @return
	 */
	public String getTypeArgumentClassNameAsDaoImport() {
		String typeArgumentClassName = getTypeArgumentClassName();
		return typeArgumentClassName.replace(".domain.", ".dao.").concat("Dao");
	}
	
	/**
	 * Retorna a string de import de Resource referente ao tipo geerico passado como argumento para o atributo coleção.
	 * 
	 * @return
	 */
	public String getTypeArgumentClassNameAsResourceImport() {
		String typeArgumentClassName = getTypeArgumentClassName();
		return typeArgumentClassName.replace(".domain.", ".service.resource.").concat("Resource");
	}
	
	/**
	 * Retorna a string de import de Endpoint referente ao tipo geerico passado como argumento para o atributo coleção.
	 * 
	 * @return
	 */
	public String getTypeArgumentClassNameAsEndpointImport() {
		String typeArgumentClassName = getTypeArgumentClassName();
		return typeArgumentClassName.replace(".domain.", ".service.endpoint.").concat("Endpoint");
	}
	
	/**
	 * Retorna o nome simples da classe referente ao tipo geerico passado como argumento para o atributo coleção.
	 * 
	 * @return
	 */
	public String getTypeArgumentSimpleClassName() {
		List<? extends TypeMirror> typeArguments = ((DeclaredType)getGetter().getReturnType()).getTypeArguments();
		if (typeArguments.size() > 0) {
			return ((TypeElement)((DeclaredType)typeArguments.get(0)).asElement()).getSimpleName().toString();
		} else {
			return null;
		}
	}
	
	/**
	 * Retorna o nome simples da classe referente ao tipo geerico passado como argumento para o atributo coleção, em camelCase.
	 * @return
	 */
	public String getTypeArgumentSimpleClassNameCamelCase() {
		String simpleClassName = getTypeArgumentSimpleClassName();
		return simpleClassName.substring(0, 1).toLowerCase().concat(simpleClassName.substring(1));
	}
	
	/**
	 * Retorna o {@link EntityMetamodel} referente ao tipo do atributo.
	 * 
	 * @return
	 */
	public EntityMetamodel getTypeEntityMetamodel() {
		TypeElement element = null;
		
		TypeMirror returnType = getGetter().getReturnType();
		if (returnType.getKind().equals(TypeKind.DECLARED)) {
			if (isListAttribute()) {
				List<? extends TypeMirror> typeArguments = ((DeclaredType)returnType).getTypeArguments();
				if (typeArguments.size() > 0) {
					element = (TypeElement)((DeclaredType)typeArguments.get(0)).asElement();
				}
			} else {
				element = (TypeElement)((DeclaredType)returnType).asElement();
			}
		}
		EntityMetamodel entityMetamodel = null;
		if (element != null) {
			entityMetamodel = GeneratorProcessor.buildEntityMetamodel(GeneratorProcessor.INSTANCE.getProcessingEnv(), element);
		}
		return entityMetamodel;
	}
	
	/**
	 * Retorna o {@link EntityPropertyMetamodel} referente ao campo descritivo da entidade. 
	 * 
	 * @return
	 */
	public EntityPropertyMetamodel getTypeEntityPropertyLabel() {
		EntityMetamodel entityMetamodel = getTypeEntityMetamodel();
		EntityPropertyMetamodel entityPropertyMetamodel = null;
		try {
			entityPropertyMetamodel = entityMetamodel.getPropertyLabel();
		} catch(RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		return entityPropertyMetamodel;
	}
	
	public boolean isSimpleAttribute() {
		List<Class<? extends Annotation>> annotationClasses = Arrays.asList(OneToOne.class, OneToMany.class, ManyToOne.class, ManyToMany.class);
		boolean found = false;
		for (Class<? extends Annotation> annotationClass : annotationClasses) {
			Annotation annotation = getGetter().getAnnotation(annotationClass);
			if (annotation != null) {
				found = true;
				break;
			}
		}
		return !found;
	}
	
	public boolean isOneToOne() {
		OneToOne oneToOne = getGetter().getAnnotation(OneToOne.class);
		return oneToOne != null;
	}
	
	public boolean isOneToOneWithMappedBy() {
		OneToOne oneToOne = getGetter().getAnnotation(OneToOne.class);
		return isOneToOne() && oneToOne.mappedBy() != null;
	}
	
	public String getOneToOneMappedByValue() {
		OneToOne oneToOne = getGetter().getAnnotation(OneToOne.class);
		return oneToOne.mappedBy();
	}
	
	public String getOneToOneMappedByValueCapitalized() {
		return StringUtil.capitalize(getOneToOneMappedByValue());
	}
	
	public boolean isOneToMany() {
		OneToMany oneToMany = getGetter().getAnnotation(OneToMany.class);
		return oneToMany != null;
	}
	
	public boolean isOneToManyWithMappedBy() {
		OneToMany oneToMany = getGetter().getAnnotation(OneToMany.class);
		return isOneToMany() && oneToMany.mappedBy() != null;
	}
	
	public String getOneToManyMappedByValue() {
		OneToMany oneToMany = getGetter().getAnnotation(OneToMany.class);
		return oneToMany.mappedBy();
	}
	
	public String getOneToManyMappedByValueCapitalized() {
		return StringUtil.capitalize(getOneToManyMappedByValue());
	}
	
	public boolean isManyToOne() {
		ManyToOne manyToOne = getGetter().getAnnotation(ManyToOne.class);
		return manyToOne != null;
	}
	
	public boolean isManyToMany() {
		ManyToMany manyToMany = getGetter().getAnnotation(ManyToMany.class);
		return manyToMany != null;
	}
	
	public boolean isManyToManyCascade() {
		ManyToMany manyToMany = getGetter().getAnnotation(ManyToMany.class);
		return manyToMany != null && (manyToMany.cascade() != null && manyToMany.cascade().length > 0);
	}
	
	public boolean isTransient() {
		Transient aTransient = getGetter().getAnnotation(Transient.class);
		return aTransient != null;
	}
	
	public boolean isEnumerated() {
		Enumerated enumerated = getGetter().getAnnotation(Enumerated.class);
		return enumerated != null;
	}

	@Override
	public String toString() {
		return "EntityProperty [propertyName=" + getPropertyNameCamelCase() + "]";
	}

	
}
