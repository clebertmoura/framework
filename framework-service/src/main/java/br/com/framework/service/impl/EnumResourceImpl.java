/**
 * 
 */
package br.com.framework.service.impl;

import br.com.framework.service.api.EnumResource;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class EnumResourceImpl implements EnumResource {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5004261945432827265L;
	
	private String key;
	private String label;

	/**
	 * 
	 */
	public EnumResourceImpl() {
	}

	/**
	 * @param key
	 * @param label
	 */
	public EnumResourceImpl(String key, String label) {
		super();
		this.key = key;
		this.label = label;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.service.api.EnumResource#getKey()
	 */
	@Override
	public String getKey() {
		return key;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.service.api.EnumResource#setKey(java.lang.String)
	 */
	@Override
	public void setKey(String key) {
		this.key = key;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.service.api.EnumResource#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.service.api.EnumResource#setLabel(java.lang.String)
	 */
	@Override
	public void setLabel(String value) {
		this.label = value;
	}

}
