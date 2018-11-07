package br.com.framework.service.impl;

import java.util.ArrayList;
import java.util.List;

import br.com.framework.service.api.BaseResource;
import br.com.framework.service.api.PaginatedResourceResponse;

/**
 * Implementação de {@link PaginatedResourceResponse}. 
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <R>
 */
public class PaginatedResourceResponseImpl<R extends BaseResource> implements PaginatedResourceResponse<R> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5546051506020221923L;
	
	private List<R> results;
	private Long totalRecords;

	public PaginatedResourceResponseImpl() {
	}

	/**
	 * @param results
	 * @param totalRecords
	 */
	public PaginatedResourceResponseImpl(List<R> results, Long totalRecords) {
		super();
		this.results = results;
		this.totalRecords = totalRecords;
	}

	@Override
	public List<R> getResults() {
		if (results == null) {
			results = new ArrayList<R>();
		}
		return results;
	}

	@Override
	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setResults(List<R> results) {
		this.results = results;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

}
