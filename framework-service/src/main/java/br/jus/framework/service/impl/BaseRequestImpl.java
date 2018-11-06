/**
 * 
 */
package br.jus.framework.service.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import br.jus.framework.service.api.BaseRequest;
import br.jus.framework.service.api.Parameter;

/**
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
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
