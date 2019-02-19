/**
 * 
 */
package br.com.framework.service.impl;

import java.io.Serializable;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.service.api.IndexedBaseResource;

/**
 * Implementação da 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária.
 * @param <E> Tipo da entidade.
 */
public abstract class IndexedBaseResourceImpl<PK extends Serializable, E extends BaseEntity<PK>> extends BaseEntityResourceImpl<PK, E> implements IndexedBaseResource<PK, E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String uuid;
	
	/**
	 * 
	 */
	public IndexedBaseResourceImpl() {
	}

	@Override
	public String getUuid() {
		return uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
