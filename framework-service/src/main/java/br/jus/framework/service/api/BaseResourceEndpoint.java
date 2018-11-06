package br.jus.framework.service.api;

import java.io.Serializable;

/**
 * Interface base dos serviços REST para acesso aos resources.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 * @param <R>
 */
public interface BaseResourceEndpoint<R extends BaseResource> extends Serializable{

}