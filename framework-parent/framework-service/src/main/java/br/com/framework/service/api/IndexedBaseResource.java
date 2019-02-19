package br.com.framework.service.api;

import java.io.Serializable;

import br.com.framework.domain.api.BaseEntity;

/**
 * Interface base para os resources indexados.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária.
 */
public interface IndexedBaseResource<PK extends Serializable, E extends BaseEntity<PK>> extends BaseEntityResource<PK, E> {
	
	/**
	 * @return
	 */
	public abstract String getUuid();

	/**
	 * @param uuid
	 */
	public abstract void setUuid(String uuid);

}