package br.jus.framework.service.api;

import java.io.Serializable;

import br.jus.framework.domain.api.BaseEntity;

/**
 * Interface base para os resources indexados.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
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