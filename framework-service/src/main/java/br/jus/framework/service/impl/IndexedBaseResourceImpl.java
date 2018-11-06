/**
 * 
 */
package br.jus.framework.service.impl;

import java.io.Serializable;

import br.jus.framework.domain.api.BaseEntity;
import br.jus.framework.service.api.IndexedBaseResource;

/**
 * Implementação da 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
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
