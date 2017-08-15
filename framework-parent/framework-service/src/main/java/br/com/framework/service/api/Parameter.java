/**
 * 
 */
package br.com.framework.service.api;

import java.io.Serializable;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public interface Parameter extends Serializable {

	public String getNome();
    public void setNome(String value);
    
    public String getValor();
    public void setValor(String value);
	
}
