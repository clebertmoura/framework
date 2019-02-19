/**
 * 
 */
package br.com.framework.service.api;

import java.io.Serializable;

import br.com.framework.domain.api.BaseEntity;

/**
 * Interface base para os resources de uma {@link BaseEntity}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
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
	public abstract Long getVersaoEntidade();
	/**
	 * @param versaoEntidade
	 */
	public abstract void setVersaoEntidade(Long versaoEntidade); 
	
}
