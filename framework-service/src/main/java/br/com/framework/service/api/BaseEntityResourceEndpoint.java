package br.com.framework.service.api;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.search.impl.PageRequest;
import br.com.framework.search.impl.PageResponse;
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
	 * @param definitely
	 * 	Indica se a remoção deve ser realizada de forma definitiva (para entidades com suporte a deleção lógica)
	 * @return
	 */
	public abstract Response remove(PK id, Boolean definitely);

	/**
	 * Search a entidade pelo sua chave primária.
	 * 
	 * @param id
	 * @param depth Profundidade da serialização do objeto.
	 * @return
	 */
	public abstract Response findById(@NotNull PK id, Integer depth);
	
	/**
	 * Search todos os registros de forma paginada
	 * 
	 * @param first
	 * @param max
	 * @param fieldOrder
	 * @param order
	 * @return
	 */
	public abstract PaginatedResourceResponse<R> findAll(Integer first, Integer max, String fieldOrder, Order order,  Integer depth);

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
	
	/**
	 * Retorna uma página de resultados de acordo com a requisição.
	 * 
	 * @param pageRequest
	 * @return
	 */
	public abstract PageResponse<R> findPage(PageRequest pageRequest);

}