package br.com.framework.service.api;

import java.io.Serializable;
import java.util.List;

/**
 * Interface que encapsula a resposta.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
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
