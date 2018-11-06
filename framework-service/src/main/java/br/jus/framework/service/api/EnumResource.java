/**
 * 
 */
package br.jus.framework.service.api;

/**
 * Interface para o resource que representa os Enums.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public interface EnumResource extends BaseResource {

	String getKey();
	void setKey(String key);
	
	String getLabel();
	void setLabel(String label);
}
