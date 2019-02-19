package br.com.framework.service.api;

import java.io.Serializable;

import br.com.framework.domain.api.BaseEntityAudited;

/**
 * Interface base dos serviços REST para acesso aos resources de entidades auditáveis.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK>
 * @param <E>
 * @param <R>
 */
public interface BaseEntityAuditedResourceEndpoint<PK extends Serializable, E extends BaseEntityAudited<PK>, R extends BaseEntityAuditedResource<PK, E>> extends BaseEntityResourceEndpoint<PK, E, R>{

}