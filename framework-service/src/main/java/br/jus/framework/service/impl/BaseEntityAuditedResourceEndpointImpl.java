/**
 * 
 */
package br.jus.framework.service.impl;

import java.io.Serializable;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.framework.domain.api.BaseEntityAudited;
import br.jus.framework.model.manager.api.BaseManager;
import br.jus.framework.search.api.Search;
import br.jus.framework.service.api.BaseEntityAuditedResource;
import br.jus.framework.service.api.BaseEntityAuditedResourceEndpoint;

/**
 * Implementação abstrata para fornecimento de API WebService dos CRUDs de entitys.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public abstract class BaseEntityAuditedResourceEndpointImpl<PK extends Serializable, E extends BaseEntityAudited<PK>, R extends BaseEntityAuditedResource<PK, E>, B extends Search<PK, E>, Manager extends BaseManager<PK, E, B>>
	extends BaseEntityResourceEndpointImpl<PK, E, R, B, Manager> implements BaseEntityAuditedResourceEndpoint<PK, E, R> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BaseEntityAuditedResourceEndpointImpl(Class<PK> entityPKClass, Class<E> entityClass,
			Class<R> entityResourceClass) {
		super(entityPKClass, entityClass, entityResourceClass);
	}

	public BaseEntityAuditedResourceEndpointImpl(String bundleMessagesName) {
		super(bundleMessagesName);
	}

	@Override
	public E fromResource(R resource, E entity, int depth) {
		entity = super.fromResource(resource, entity, depth);
		if (entity != null) {
			entity.setCreationDate(resource.getCreationDate());
			entity.setLastModificationDate(resource.getLastModificationDate());
			entity.setStatus(resource.getStatus());
			entity.setCreationAuthor(resource.getCreationAuthor());
			entity.setLastModificationAuthor(resource.getLastModificationAuthor());
		}
		return entity;
	}
	
	@Override
	public R toResource(E entity, R resource, int depth) {
		resource = super.toResource(entity, resource, depth);
		if (resource != null) {
			resource.setCreationDate(entity.getCreationDate());
			resource.setLastModificationDate(entity.getLastModificationDate());
			resource.setStatus(entity.getStatus());
			resource.setCreationAuthor(entity.getCreationAuthor());
			resource.setLastModificationAuthor(entity.getLastModificationAuthor());
		}
		return resource;
	}

}
