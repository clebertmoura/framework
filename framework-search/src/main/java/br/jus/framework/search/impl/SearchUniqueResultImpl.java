/**
 * 
 */
package br.jus.framework.search.impl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import br.jus.framework.search.api.SearchUniqueResult;

/**
 * Implementação do {@link SearchUniqueResult}.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
@XmlRootElement
public class SearchUniqueResultImpl<R extends Serializable> implements SearchUniqueResult<R> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private R result = null;
	private long executionTime = -1;

	/**
	 * @param result
	 * @param executionTime
	 */
	public SearchUniqueResultImpl(R result, long executionTime) {
		super();
		this.result = result;
		this.executionTime = executionTime;
	}

	@Override
	public long getExecutionTime() {
		return executionTime;
	}

	@Override
	public R getUniqueResult() {
		return result;
	}

}
