package br.com.framework.service.api;

import java.io.Serializable;
import java.util.List;

/**
 * Interface para o objeto de retorno das requisicoes de consulta.  
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 * 
 * @param <R>
 */
public interface PaginatedResourceResponse<R extends BaseResource> extends Serializable {

	/**
	 * @return the results
	 */
	public abstract List<R> getResults();
	
	/**
	 * @param results
	 */
	public void setResults(List<R> results);

	/**
	 * @return the totalRecords
	 */
	public abstract Long getTotalRecords();
	
	/**
	 * @param totalRecords
	 */
	public void setTotalRecords(Long totalRecords);

}