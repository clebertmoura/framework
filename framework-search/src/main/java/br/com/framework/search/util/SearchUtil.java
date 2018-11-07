package br.com.framework.search.util;

import java.io.Serializable;
import java.util.List;

import br.com.framework.search.api.SearchResult;
import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.search.impl.Operator;
import br.com.framework.search.impl.Ordering;
import br.com.framework.search.impl.Restriction;
import br.com.framework.search.impl.SearchResultImpl;
import br.com.framework.search.impl.SearchUniqueResultImpl;
import br.com.framework.search.impl.Ordering.Order;

/**
 * Classe utilitaria para construção dos objetos.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class SearchUtil implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static SearchUtil INSTANCE;

	/**
	 * Retorna uma instancia singleton.
	 * 
	 * @return
	 */
	public static SearchUtil instance() {
		if (INSTANCE == null) {
			INSTANCE = newInstance();
		}
		return INSTANCE;
	}
	
	/**
	 * Retorna uma nova instancia.
	 * 
	 * @return
	 */
	public static SearchUtil newInstance() {
		return new SearchUtil();
	}
	
	/**
	 * Cria uma implementação do {@link SearchUniqueResult}
	 * 
	 * @param resultClass
	 * @param result
	 * @param executionTimeMillis
	 * 
	 * @return
	 */
	public <R extends Serializable> SearchUniqueResult<R> searchUniqueResult(Class<R> resultClass, R result, long executionTimeMillis) {
		return new SearchUniqueResultImpl<R>(result, executionTimeMillis);
	}
	
	/**
	 * Cria uma implementação do {@link SearchResult}
	 * 
	 * @param resultClass
	 * @param resultList
	 * @param executionTimeMillis
	 * @return
	 */
	public <R extends Serializable> SearchResult<R> searchResult(Class<R> resultClass, List<R> resultList, long executionTimeMillis) {
		return new SearchResultImpl<R>(resultList, executionTimeMillis);
	}
	
	/**
	 * Cria uma {@link Restriction}
	 * 
	 * @param field
	 * @param operator
	 * @param value
	 * @return
	 */
	public Restriction restriction(String field, Operator operator, Object value) {
		return new Restriction(field, operator, value);
	}
	
	/**
	 * Cria uma {@link Ordering} para o field informado.
	 * 
	 * @param field
	 * @param order
	 * @return
	 */
	public Ordering order(String field, Order order) {
		return new Ordering(field, order);
	}

}
