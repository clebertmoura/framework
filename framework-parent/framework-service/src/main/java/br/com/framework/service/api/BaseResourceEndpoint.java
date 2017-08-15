package br.com.framework.service.api;

import java.io.Serializable;

/**
 * Interface base dos serviços REST para acesso aos resources.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <R>
 */
public interface BaseResourceEndpoint<R extends BaseResource> extends Serializable{

}