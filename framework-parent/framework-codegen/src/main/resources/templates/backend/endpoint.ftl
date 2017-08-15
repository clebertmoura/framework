package ${modulePackage}.service.endpoint;

import java.util.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;
import br.com.framework.service.util.LoadRelatedEntityResource;
import ${modulePackage}.dao.${entityName}Dao;
import ${modulePackage}.domain.${entityName};
import ${modulePackage}.manager.${entityName}Manager;
import ${modulePackage}.service.resource.${entityName}Resource;

<#list entityMetamodel.propertiesDaos as entityProperty>
	<#if !entityProperty.transient && !entityProperty.simpleAttribute>
		<#if entityProperty.listAttribute>
import ${entityProperty.typeArgumentClassName};
import ${entityProperty.typeArgumentClassNameAsDaoImport};
import ${entityProperty.typeArgumentClassNameAsResourceImport};
import ${entityProperty.typeArgumentClassNameAsEndpointImport};
		<#else>
			<#if !entityProperty.primitiveAttribute>
import ${entityProperty.typeClassNameNoArgument};
import ${entityProperty.typeClassNameNoArgumentAsDaoImport};
				<#if entityProperty.imageAttribute>
import ${entityProperty.typeClassNameNoArgumentAsEndpointImport};
import ${entityProperty.typeClassNameNoArgumentAsResourceImport};
				</#if>
			</#if>
		</#if>
	</#if>
</#list>

/**
 * Endpoint de {@link ${entityName}}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/${entityNameCamelCase}")
public class ${entityName}Endpoint extends <#if entityMetamodel.baseEntityAudited>BaseEntityAuditedResourceEndpointImpl<#else>BaseEntityResourceEndpointImpl</#if><Long, ${entityName}, ${entityName}Resource, ${entityName}Dao, ${entityName}Manager>{

	private static final long serialVersionUID = -1L;
	
	<#list entityMetamodel.propertiesDaos as entityProperty>
		<#if !entityProperty.transient && !entityProperty.simpleAttribute>
			<#if entityProperty.listAttribute>
	@Inject
	private ${entityProperty.typeArgumentSimpleClassName}Dao ${entityProperty.typeArgumentSimpleClassNameCamelCase}Dao;
	@Inject
	private ${entityProperty.typeArgumentSimpleClassName}Endpoint ${entityProperty.typeArgumentSimpleClassNameCamelCase}Endpoint;
			<#else>
	@Inject
	private ${entityProperty.typeClassSimpleName}Dao ${entityProperty.typeClassSimpleNameCamelCase}Dao;
	@Inject
	private ${entityProperty.typeClassSimpleName}Endpoint ${entityProperty.typeClassSimpleNameCamelCase}Endpoint;
			</#if>
		</#if>
	</#list>
	
	@Override
	@Inject
	protected void setManager(${entityName}Manager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(${entityName}Dao search) {
		super.setSearch(search);
	}

	@Override
	public ${entityName} fromResource(${entityName} entity, ${entityName}Resource resource) {
		if (entity == null) {
			entity = new ${entityName}();
		}
		// inicializa os atributos da entidade.
		<#list entityProperties as entityProperty>
			<#if !entityProperty.transient && entityProperty.simpleAttribute>
		entity.set${entityProperty.propertyName}(resource.get${entityProperty.propertyName}());
			</#if>
		</#list>
		// inicializa entidades relacionadas
		<#list entityProperties as entityProperty>
			<#if !entityProperty.transient && !entityProperty.simpleAttribute>
				<#if entityProperty.listAttribute>
					<#if !entityProperty.imageAttribute>
						<#if entityProperty.oneToOne>
							<#if entityProperty.oneToOneWithMappedBy>
		loadEntityRelations(entity.get${entityProperty.propertyName}(), resource.get${entityProperty.propertyName}(), ${entityProperty.typeArgumentSimpleClassNameCamelCase}Dao, new LoadRelatedEntityResource<${entityName}, ${entityProperty.typeArgumentSimpleClassName}, ${entityProperty.typeArgumentSimpleClassName}Resource>(entity) {
			@Override
			public ${entityProperty.typeArgumentSimpleClassName} loadRelatedEntityResource(${entityProperty.typeArgumentSimpleClassName} relatedEntity,
					${entityProperty.typeArgumentSimpleClassName}Resource relatedResource) {
				relatedEntity = ${entityProperty.typeArgumentSimpleClassNameCamelCase}Endpoint.fromResource(relatedEntity, relatedResource);
				relatedEntity.set${entityProperty.oneToOneMappedByValueCapitalized}(getParentEntity());
				return relatedEntity;
			}
		});
							<#else>
		loadEntityRelations(entity.get${entityProperty.propertyName}(), resource.get${entityProperty.propertyName}(), ${entityProperty.typeArgumentSimpleClassNameCamelCase}Dao);
							</#if>
						<#elseif entityProperty.oneToMany>
							<#if entityProperty.oneToManyWithMappedBy>
		loadEntityRelations(entity.get${entityProperty.propertyName}(), resource.get${entityProperty.propertyName}(), ${entityProperty.typeArgumentSimpleClassNameCamelCase}Dao, new LoadRelatedEntityResource<${entityName}, ${entityProperty.typeArgumentSimpleClassName}, ${entityProperty.typeArgumentSimpleClassName}Resource>(entity) {
			@Override
			public ${entityProperty.typeArgumentSimpleClassName} loadRelatedEntityResource(${entityProperty.typeArgumentSimpleClassName} relatedEntity,
					${entityProperty.typeArgumentSimpleClassName}Resource relatedResource) {
				relatedEntity = ${entityProperty.typeArgumentSimpleClassNameCamelCase}Endpoint.fromResource(relatedEntity, relatedResource);
				relatedEntity.set${entityProperty.oneToManyMappedByValueCapitalized}(getParentEntity());
				return relatedEntity;
			}
		});
							<#else>
		loadEntityRelations(entity.get${entityProperty.propertyName}(), resource.get${entityProperty.propertyName}(), ${entityProperty.typeArgumentSimpleClassNameCamelCase}Dao);
							</#if>
						<#elseif entityProperty.manyToMany>
							<#if entityProperty.manyToManyCascade>
		loadEntityRelations(entity.get${entityProperty.propertyName}(), resource.get${entityProperty.propertyName}(), ${entityProperty.typeArgumentSimpleClassNameCamelCase}Dao, new LoadRelatedEntityResource<${entityName}, ${entityProperty.typeArgumentSimpleClassName}, ${entityProperty.typeArgumentSimpleClassName}Resource>(entity) {
			@Override
			public ${entityProperty.typeArgumentSimpleClassName} loadRelatedEntityResource(${entityProperty.typeArgumentSimpleClassName} relatedEntity,
					${entityProperty.typeArgumentSimpleClassName}Resource relatedResource) {
				relatedEntity = ${entityProperty.typeArgumentSimpleClassNameCamelCase}Endpoint.fromResource(relatedEntity, relatedResource);
				return relatedEntity;
			}
		});
							<#else>
		loadEntityRelations(entity.get${entityProperty.propertyName}(), resource.get${entityProperty.propertyName}(), ${entityProperty.typeArgumentSimpleClassNameCamelCase}Dao);
							</#if>
						</#if>
					</#if>
				<#else>
					<#if !entityProperty.imageAttribute>
		${entityProperty.typeClassSimpleName} ${entityProperty.typeClassSimpleNameCamelCase} = loadEntityRelation(entity.get${entityProperty.propertyName}(), resource.get${entityProperty.propertyName}(), ${entityProperty.typeClassSimpleNameCamelCase}Dao);
		entity.set${entityProperty.propertyName}(${entityProperty.typeClassSimpleNameCamelCase});
					<#else>
		${entityProperty.typeClassSimpleName} ${entityProperty.propertyNameCamelCase} = loadEntityRelation(entity.get${entityProperty.propertyName}(), resource.get${entityProperty.propertyName}(), ${entityProperty.typeClassSimpleNameCamelCase}Dao, new LoadRelatedEntityResource<${entityName}, ${entityProperty.typeClassSimpleName}, ${entityProperty.typeClassSimpleName}Resource>(entity) {
			public ${entityProperty.typeClassSimpleName} loadRelatedEntityResource(${entityProperty.typeClassSimpleName} entity, ${entityProperty.typeClassSimpleName}Resource resource) {
				return ${entityProperty.typeClassSimpleNameCamelCase}Endpoint.fromResource(entity, resource);
			}
		});
		entity.set${entityProperty.propertyName}(${entityProperty.propertyNameCamelCase});
					</#if>
				</#if>
			</#if>
		</#list>
		return entity;
	}

	@Override
	protected ${entityName}Resource toResource(${entityName} entity) {
		return new ${entityName}Resource(entity);
	}
}