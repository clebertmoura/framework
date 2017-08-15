package br.com.framework.search.api;

import java.io.Serializable;
import java.util.List;

/**
 * Interface para o resultado da pesquisa,
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <R> Tipo do Resultado.
 */
public interface SearchResult<R extends Serializable> extends Serializable {

	/**
	 * Retorna a lista com os resultados encontrados. 
	 * @return
	 */
	List<R> getResults();
	
	/**
	 * Retorna o tempo de execução em millisegundos.
	 * @return
	 */
	long getExecutionTime();
	
}
