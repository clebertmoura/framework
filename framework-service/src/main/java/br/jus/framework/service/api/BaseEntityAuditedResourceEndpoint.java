package br.jus.framework.service.api;

import java.io.Serializable;

import br.jus.framework.domain.api.BaseEntityAudited;

/**
 * Interface base dos serviços REST para acesso aos resources de entidades auditáveis.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 * @param <PK>
 * @param <E>
 * @param <R>
 */
public interface BaseEntityAuditedResourceEndpoint<PK extends Serializable, E extends BaseEntityAudited<PK>, R extends BaseEntityAuditedResource<PK, E>> extends BaseEntityResourceEndpoint<PK, E, R>{

}