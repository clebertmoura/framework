package br.jus.framework.search.api;

import java.io.Serializable;

/**
 * Interface para o resultado da pesquisa,
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
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
