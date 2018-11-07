/**
 * 
 */
package br.com.framework.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.service.api.BaseResponse;
import br.com.framework.service.api.Error;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@XmlRootElement
public class BaseResponseImpl<R> implements BaseResponse<R> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Error> errors;
	private R response;

	/**
	 * 
	 */
	public BaseResponseImpl() {
	}

	/**
	 * @param response
	 */
	public BaseResponseImpl(R response) {
		super();
		this.response = response;
	}
	
	/**
	 * @param response
	 * @param errors
	 */
	public BaseResponseImpl(R response, List<Error> errors) {
		super();
		this.errors = errors;
		this.response = response;
	}

	@Override
	public List<Error> getErros() {
		if (errors == null) {
			errors = new ArrayList<Error>();
		}
		return errors;
	}

	@Override
	public R getResponse() {
		return response;
	}

}
