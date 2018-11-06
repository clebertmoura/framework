/**
 * 
 */
package br.jus.framework.service.api;

import java.io.Serializable;

/**
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public interface Parameter extends Serializable {

	public String getNome();
    public void setNome(String value);
    
    public String getValor();
    public void setValor(String value);
	
}
