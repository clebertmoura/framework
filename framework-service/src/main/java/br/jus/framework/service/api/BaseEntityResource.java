/**
 * 
 */
package br.jus.framework.service.api;

import java.io.Serializable;

import br.jus.framework.domain.api.BaseEntity;

/**
 * Interface base para os resources de uma {@link BaseEntity}.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 * @param <PK> Tipo da chave primária.
 * @param <E> Tipo da entidade.
 */
public interface BaseEntityResource<PK extends Serializable, E extends BaseEntity<PK>> extends BaseResource {

	/**
	 * @return
	 */
	public abstract PK getId();

	/**
	 * @param id
	 */
	public abstract void setId(PK id);
	
	/**
	 * @return
	 */
	public abstract Long getVersion();
	/**
	 * @param version
	 */
	public abstract void setVersion(Long version); 
	
}
