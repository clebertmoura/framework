package br.com.framework.service.api;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.search.impl.Ordering.Order;
import br.com.framework.service.impl.FindByRestrictionsRequest;

/**
 * Interface base dos serviços REST para acesso aos resources de entidades.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK>
 * @param <E>
 * @param <R>
 */
public interface BaseEntityResourceEndpoint<PK extends Serializable, E extends BaseEntity<PK>, R extends BaseEntityResource<PK, E>> extends BaseResourceEndpoint<R>{
	
	/**
	 * Insere uma nova entidade no banco de dados.
	 * 
	 * @param entity
	 * @return
	 */
	public abstract Response insert(R entity);

	/**
	 * Altera uma entidade existente no banco de dados.
	 * 
	 * @param id
	 * @param entity
	 * @return
	 */
	public abstract Response update(PK id, R entity);

	/**
	 * Remove uma entidade existente do banco de dados.
	 * 
	 * @param id
	 * @return
	 */
	public abstract Response remove(PK id);

	/**
	 * Search a entidade pelo sua chave primária.
	 * 
	 * @param id
	 * @return
	 */
	public abstract Response findById(PK id);
	
	/**
	 * Search todos os registros de forma paginada
	 * 
	 * @param first
	 * @param max
	 * @param fieldOrder
	 * @param order
	 * @return
	 */
	public abstract PaginatedResourceResponse<R> findAll(Integer first, Integer max, String fieldOrder, Order order);

	/**
	 * Search a quantidade de entidades existentes.
	 * 
	 * @return
	 */
	public abstract Long getCountFindAll();

	/**
	 * Search entidades com filtro pelo campo informado com ordenação e paginação.
	 * 
	 * @param request
	 * @return
	 */
	public abstract PaginatedResourceResponse<R> findByRestrictions(FindByRestrictionsRequest request);

	/**
	 * Search a quantidade de entidades existentes pelo mapa de filtros.
	 * 
	 * @param request
	 * @return
	 */
	public abstract Long getCountFindByRestrictions(FindByRestrictionsRequest request);

}