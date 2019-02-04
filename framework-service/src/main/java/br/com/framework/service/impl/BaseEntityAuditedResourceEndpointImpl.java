/**
 * 
 */
package br.com.framework.service.impl;

import java.io.Serializable;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.framework.domain.api.BaseEntityAudited;
import br.com.framework.model.manager.api.BaseManager;
import br.com.framework.search.api.Search;
import br.com.framework.service.api.BaseEntityAuditedResource;
import br.com.framework.service.api.BaseEntityAuditedResourceEndpoint;

/**
 * Implementação abstrata para fornecimento de API WebService dos CRUDs de entitys.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
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
