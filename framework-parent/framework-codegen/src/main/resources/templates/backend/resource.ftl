package ${modulePackage}.service.resource;

import java.util.*;
import java.time.*;

import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.framework.service.impl.BaseEntityResourceImpl;
import br.com.framework.service.impl.BaseEntityAuditedResourceImpl;
import ${modulePackage}.domain.${entityName};

<#list entityMetamodel.propertiesDaos as entityProperty>
	<#if !entityProperty.transient && !entityProperty.simpleAttribute>
		<#if entityProperty.listOrSetAttribute>
import ${entityProperty.typeArgumentClassName};
import ${entityProperty.typeArgumentClassNameAsResourceImport};
		<#else>
			<#if !entityProperty.primitiveAttribute>
import ${entityProperty.typeClassNameNoArgument};
import ${entityProperty.typeClassNameNoArgumentAsResourceImport};
			</#if>
		</#if>
	</#if>
</#list>
<#list entityMetamodel.propertiesEnums as entityProperty>
import ${entityProperty.typeClassNameNoArgument};
</#list>

/**
 * Resource da entidade {@link ${entityName}}.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@XmlRootElement
public class ${entityName}Resource extends <#if entityMetamodel.baseEntityAudited>BaseEntityAuditedResourceImpl<#else>BaseEntityResourceImpl</#if><Long, ${entityName}> {

	private static final long serialVersionUID = -1L;
	
	<#list entityProperties as entityProperty>
		<#if !entityProperty.transient && entityProperty.simpleAttribute>
	private ${entityProperty.typeClassSimpleName} ${entityProperty.propertyNameCamelCase};
		</#if>
	</#list>
	<#list entityProperties as entityProperty>
		<#if !entityProperty.transient && !entityProperty.simpleAttribute>
			<#if entityProperty.listOrSetAttribute>
	private <#if entityProperty.listAttribute>List<#else>Set</#if><${entityProperty.typeArgumentSimpleClassName}Resource> ${entityProperty.propertyNameCamelCase};
			<#else>
	private ${entityProperty.typeClassSimpleName}Resource ${entityProperty.propertyNameCamelCase};
			</#if>
		</#if>
	</#list>
	
	public ${entityName}Resource() {
		super();
	}

	/**
	 * @param entity
	 * @param onlyId
	 */
	public ${entityName}Resource(${entityName} entity, boolean onlyId) {
		super(entity, onlyId);
	}

	/**
	 * @param entity
	 */
	public ${entityName}Resource(${entityName} entity) {
		super(entity);
	}

	@Override
	public void loadFromEntity(${entityName} entity) {
		super.loadFromEntity(entity);
	<#list entityProperties as entityProperty>
		<#if !entityProperty.transient && entityProperty.simpleAttribute>
		set${entityProperty.propertyName}(entity.get${entityProperty.propertyName}());
		</#if>
	</#list>
	<#list entityProperties as entityProperty>
		<#if !entityProperty.transient>
			<#if (entityProperty.oneToMany || entityProperty.manyToMany)>
		Iterator<${entityProperty.typeArgumentSimpleClassName}> it${entityProperty.propertyName} = entity.get${entityProperty.propertyName}().iterator();
		while (it${entityProperty.propertyName}.hasNext()) {
			${entityProperty.typeArgumentSimpleClassName} element = it${entityProperty.propertyName}.next();
			this.get${entityProperty.propertyName}().add(new ${entityProperty.typeArgumentSimpleClassName}Resource(element));
		}
			<#else>
				<#if entityProperty.manyToOne || entityProperty.oneToOne>
		set${entityProperty.propertyName}(new ${entityProperty.typeClassSimpleName}Resource(entity.get${entityProperty.propertyName}()));
				</#if>
			</#if>
		</#if>
	</#list>
	}
	
	public ${entityMetamodel.propertyId.typeClassSimpleName} get${entityMetamodel.propertyId.propertyName}() {
		return this.${entityMetamodel.propertyId.propertyNameCamelCase};
	}
	public void set${entityMetamodel.propertyId.propertyName}(${entityMetamodel.propertyId.typeClassSimpleName} ${entityMetamodel.propertyId.propertyNameCamelCase}) {
		this.${entityMetamodel.propertyId.propertyNameCamelCase} = ${entityMetamodel.propertyId.propertyNameCamelCase};
	}

	<#list entityProperties as entityProperty>
		<#if !entityProperty.transient && entityProperty.simpleAttribute>
			<#if entityProperty.temporalAttribute>
				<#if entityProperty.temporalDateAttribute>
	@JsonFormat(pattern = "dd-MM-yyyy")
				<#elseif entityProperty.temporalTimeAttribute>
	@JsonFormat(pattern = "HH:mm.Z")
				<#else>
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm.Z")
				</#if>	
			</#if>
	public ${entityProperty.typeClassSimpleName} get${entityProperty.propertyName}() {
		return this.${entityProperty.propertyNameCamelCase};
	}
	public void set${entityProperty.propertyName}(${entityProperty.typeClassSimpleName} ${entityProperty.propertyNameCamelCase}) {
		this.${entityProperty.propertyNameCamelCase} = ${entityProperty.propertyNameCamelCase};
	}
		</#if>
	</#list>
	<#list entityProperties as entityProperty>
		<#if !entityProperty.transient && !entityProperty.simpleAttribute>
			<#if entityProperty.listOrSetAttribute>
	public <#if entityProperty.listAttribute>List<#else>Set</#if><${entityProperty.typeArgumentSimpleClassName}Resource> get${entityProperty.propertyName}() {
		if (${entityProperty.propertyNameCamelCase} == null) {
			${entityProperty.propertyNameCamelCase} = new <#if entityProperty.listAttribute>ArrayList<#else>HashSet</#if><${entityProperty.typeArgumentSimpleClassName}Resource>();
		}
		return this.${entityProperty.propertyNameCamelCase};
	}
	public void set${entityProperty.propertyName}(<#if entityProperty.listAttribute>List<#else>Set</#if><${entityProperty.typeArgumentSimpleClassName}Resource> ${entityProperty.propertyNameCamelCase}) {
		this.${entityProperty.propertyNameCamelCase} = ${entityProperty.propertyNameCamelCase};
	}
			<#else>
	public ${entityProperty.typeClassSimpleName}Resource get${entityProperty.propertyName}() {
		return this.${entityProperty.propertyNameCamelCase};
	}
	public void set${entityProperty.propertyName}(${entityProperty.typeClassSimpleName}Resource ${entityProperty.propertyNameCamelCase}) {
		this.${entityProperty.propertyNameCamelCase} = ${entityProperty.propertyNameCamelCase};
	}
			</#if>
		</#if>
	</#list>

}

