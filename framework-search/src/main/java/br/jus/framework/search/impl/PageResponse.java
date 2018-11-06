/**
 * 
 */
package br.jus.framework.search.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a requisição a uma página de resultados.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public class PageResponse<E> {
	/**
	 * Lista dos resultados
	 */
	private List<E> results;
	/**
     * Quantidade total de registros.
     */
    private Long totalRegisters;
    
	public PageResponse() {
		super();
	}
	
	public PageResponse(List<E> items, Long count) {
		super();
		this.results = items;
		this.totalRegisters = count;
	}

	public List<E> getResults() {
		if (results == null) {
			results = new ArrayList<>();
		}
		return results;
	}
	public void setResults(List<E> items) {
		this.results = items;
	}
	public Long getTotalRegisters() {
		return totalRegisters;
	}
	public void setTotalRegisters(Long count) {
		this.totalRegisters = count;
	}

}
