package br.com.framework.piloto.core.domain;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import br.com.framework.search.indexer.api.IndexedBaseEntity;

/**
 * Superclasse {@link MappedSuperclass} que server de base para todas as
 * entidades que necessitam suportar exclusão lógica.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 * 
 */
@MappedSuperclass
public abstract class IndexedBaseEntityAuditedImpl<PK extends Serializable> extends BaseEntityAuditedImpl<PK>
	implements IndexedBaseEntity<PK> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 967907861024382703L;
	
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
