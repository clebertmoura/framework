
package br.jus.framework.piloto.core.domain;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import br.jus.framework.search.indexer.api.IndexedBaseEntity;

/**
 * Implementação abstrata da {@link IndexedBaseEntity}.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 * @param <PK> Tipo da chave primária.
 */
@MappedSuperclass
public abstract class IndexedBaseEntityImpl<PK extends Serializable> extends BaseEntityImpl<PK>
		implements IndexedBaseEntity<PK> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6159976136863458264L;
	
	private String uuid;

	@Override
	@Transient
	public String getUuid() {
		return uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
