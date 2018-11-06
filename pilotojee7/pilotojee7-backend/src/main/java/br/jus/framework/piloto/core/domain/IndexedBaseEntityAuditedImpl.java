package br.jus.framework.piloto.core.domain;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import br.jus.framework.search.indexer.api.IndexedBaseEntity;

/**
 * Superclasse {@link MappedSuperclass} que server de base para todas as
 * entidades que necessitam suportar exclusão lógica.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
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
