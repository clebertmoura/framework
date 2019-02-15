/**
 * 
 */
package br.com.framework.service.util;

import java.util.List;

import br.com.framework.model.error.impl.ErrorBuilder;
import br.com.framework.model.error.impl.ErrorDefault;
import br.com.framework.service.api.BaseResponse;
import br.com.framework.service.api.EnumResource;
import br.com.framework.service.impl.BaseResponseImpl;
import br.com.framework.service.impl.EnumResourceImpl;


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
		return new BaseResponseImpl<>(responseValue);
	}
	
	/**
	 * Cria um {@link BaseResponse} com o responseValue informado e a lista de {@link Error}.
	 * 
	 * @param responseValue
	 * @param errors
	 * @return
	 */
	public static <R> BaseResponse<R> buildResponse(R responseValue, List<Error> errors) {
		return new BaseResponseImpl<>(responseValue, errors);
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
	public static ErrorDefault buildError(String code, String desc) {
		return ErrorBuilder.buildError(desc);
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
