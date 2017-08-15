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
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public abstract class BaseEntityAuditedResourceEndpointImpl<PK extends Serializable, E extends BaseEntityAudited<PK>, R extends BaseEntityAuditedResource<PK, E>, B extends Search<PK, E>, Manager extends BaseManager<PK, E, B>>
	extends BaseEntityResourceEndpointImpl<PK, E, R, B, Manager> implements BaseEntityAuditedResourceEndpoint<PK, E, R> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BaseEntityAuditedResourceEndpointImpl() {
		super();
	}

	public BaseEntityAuditedResourceEndpointImpl(String bundleMessagesName) {
		super(bundleMessagesName);
	}
	
	@Override
	protected E fromResource(E entity, R resource) {
		entity = super.fromResource(entity, resource);
		entity.setCreateDate(resource.getCreateDate());
		entity.setLastModifiedDate(resource.getLastModifiedDate());
		entity.setStatus(resource.getStatus());
		entity.setUserLogin(resource.getUserLogin());
		return entity;
	}

}
