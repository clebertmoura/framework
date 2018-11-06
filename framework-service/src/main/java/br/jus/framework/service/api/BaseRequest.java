package br.jus.framework.service.api;

import java.io.Serializable;
import java.util.List;

/**
 * Interface que encapsula a resposta.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 * @param <R>
 */
public interface BaseRequest extends Serializable {

	/**
	 * @return
	 */
	public List<Parameter> getParams();
	/**
	 * @param params
	 */
	public void setParams(List<Parameter> params);
	
}
