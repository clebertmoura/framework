/**
 * 
 */
package br.jus.framework.search.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import br.jus.framework.search.api.SearchResult;

/**
 * Implementação do {@link SearchResult}.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
@XmlRootElement
public class SearchResultImpl<R extends Serializable> implements SearchResult<R> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<R> results = new ArrayList<R>();
	private long executionTime = -1;

	/**
	 * @param results
	 * @param executionTime
	 */
	public SearchResultImpl(List<R> results, long executionTime) {
		super();
		this.results = results;
		this.executionTime = executionTime;
	}

	@Override
	public List<R> getResults() {
		return results;
	}
	
	@Override
	public long getExecutionTime() {
		return executionTime;
	}

}
