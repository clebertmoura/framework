/**
 * 
 */
package br.com.framework.service.util;

import java.util.List;

import br.com.framework.service.api.BaseResponse;
import br.com.framework.service.api.EnumResource;
import br.com.framework.service.api.Error;
import br.com.framework.service.impl.BaseResponseImpl;
import br.com.framework.service.impl.EnumResourceImpl;
import br.com.framework.service.impl.ErrorImpl;


/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class UtilBuilder {

	/**
	 * 
	 */
	private UtilBuilder() {
	}
	
	/**
	 * Cria um {@link BaseResponse} com o responseValue informado.
	 * 
	 * @param responseValue
	 * @return
	 */
	public static <R> BaseResponse<R> buildResponse(R responseValue) {
		return new BaseResponseImpl<R>(responseValue);
	}
	
	/**
	 * Cria um {@link BaseResponse} com o responseValue informado e a lista de {@link Error}.
	 * 
	 * @param responseValue
	 * @param errors
	 * @return
	 */
	public static <R> BaseResponse<R> buildResponse(R responseValue, List<Error> errors) {
		return new BaseResponseImpl<R>(responseValue, errors);
	}
	
	/**
	 * Cria um {@link BaseResponse} com o responseValue informado e o {@link Error}.
	 *  
	 * @param responseValue
	 * @param error
	 * @return
	 */
	public static <R> BaseResponse<R> buildResponse(R responseValue, Error error) {
		BaseResponse<R> response = buildResponse(responseValue);
		response.getErros().add(error);
		return response;
	}
	
	/**
	 * Cria um {@link Error}
	 * 
	 * @param code
	 * @param desc
	 * @return
	 */
	public static Error buildError(String code, String desc) {
		return new ErrorImpl(code, desc);
	}
	
	/**
	 * Cria um {@link EnumResource}
	 * 
	 * @param key
	 * @param label
	 * @return
	 */
	public static EnumResource buildEnumResource(String key, String label) {
		return new EnumResourceImpl(key, label);
	}

}
