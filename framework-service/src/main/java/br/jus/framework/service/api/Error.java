/**
 * 
 */
package br.jus.framework.service.api;

import java.io.Serializable;

/**
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
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
