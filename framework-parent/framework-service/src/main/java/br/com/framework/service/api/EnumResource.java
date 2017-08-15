/**
 * 
 */
package br.com.framework.service.api;

/**
 * Interface para o resource que representa os Enums.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public interface EnumResource extends BaseResource {

	String getKey();
	void setKey(String key);
	
	String getLabel();
	void setLabel(String label);
}
