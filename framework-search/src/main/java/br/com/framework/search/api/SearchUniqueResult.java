package br.com.framework.search.api;

import java.io.Serializable;

/**
 * Interface para o resultado da pesquisa,
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <R>
 */
public interface SearchUniqueResult<R> extends Serializable {

	/**
	 * Retorna o resultado encontrado. 
	 * @return
	 */
	R getUniqueResult();
	
	/**
	 * Retorna o tempo de execução em millisegundos.
	 * @return
	 */
	long getExecutionTime();
	
}
