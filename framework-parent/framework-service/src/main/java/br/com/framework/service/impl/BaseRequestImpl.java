/**
 * 
 */
package br.com.framework.service.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.service.api.BaseRequest;
import br.com.framework.service.api.Parameter;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@XmlRootElement
public class BaseRequestImpl implements BaseRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4226840853107514175L;
	private List<Parameter> params;

	/**
	 * 
	 */
	public BaseRequestImpl() {
	}

	public BaseRequestImpl(List<Parameter> params) {
		super();
		this.params = params;
	}

	public List<Parameter> getParams() {
		return params;
	}

	public void setParams(List<Parameter> params) {
		this.params = params;
	}

}
