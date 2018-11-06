package br.jus.framework.service.api;

import java.io.Serializable;
import java.util.List;

/**
 * Interface para o objeto de retorno das requisicoes de consulta.  
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
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