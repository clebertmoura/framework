/**
 * 
 */
package br.com.framework.service.api;

import java.io.Serializable;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public interface Error extends Serializable {

	/**
	 * @return
	 */
	String getCode();
	/**
	 * @param code
	 */
	void setCode(String code);
	
	/**
	 * @return
	 */
	String getDescription();
	/**
	 * @param description
	 */
	void setDescription(String description);
	
}
