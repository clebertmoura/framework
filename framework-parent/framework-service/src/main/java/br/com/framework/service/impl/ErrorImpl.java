/**
 * 
 */
package br.com.framework.service.impl;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.service.api.Error;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@XmlRootElement
public class ErrorImpl implements Error {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String description;

	/**
	 * 
	 */
	public ErrorImpl() {
	}

	/**
	 * @param code
	 * @param description
	 */
	public ErrorImpl(String code, String description) {
		super();
		this.code = code;
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.service.domain.api.Erro#getCode()
	 */
	@Override
	public String getCode() {
		return code;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.service.domain.api.Erro#setCode(java.lang.String)
	 */
	@Override
	public void setCode(String code) {
		this.code = code;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.service.domain.api.Erro#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.service.domain.api.Erro#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

}
