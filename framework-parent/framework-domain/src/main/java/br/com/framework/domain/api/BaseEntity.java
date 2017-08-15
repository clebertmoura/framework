package br.com.framework.domain.api;

import java.io.Serializable;

/**
 * Interface base para as entidades de domínio. 
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária.
 */
public interface BaseEntity<PK extends Serializable> extends Serializable {

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