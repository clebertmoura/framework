package br.jus.framework.service.api;

import java.io.Serializable;
import java.util.List;

/**
 * Interface que encapsula a resposta.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 * @param <R>
 */
public interface BaseResponse<R> extends Serializable {

	/**
	 * @return
	 */
	public List<Error> getErros();
	
	/**
	 * @return
	 */
	public R getResponse();
	
}
