/**
 * 
 */
package br.jus.framework.service.impl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import br.jus.framework.domain.api.BaseEntity;
import br.jus.framework.service.api.BaseEntityResource;
import br.jus.framework.service.api.BaseResource;

/**
 * Implementação da interface {@link BaseResource}
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 * @param <PK> Tipo da chave primária.
 * @param <E> Tipo da entidade.
 */
@XmlRootElement
public abstract class BaseEntityResourceImpl<PK extends Serializable, E extends BaseEntity<PK>> extends BaseResourceImpl implements BaseEntityResource<PK, E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PK id;
	private Long version;

	/**
	 * 
	 */
	public BaseEntityResourceImpl() {
	}
	
	/**
	 * @param entity
	 */
	public BaseEntityResourceImpl(final E entity) {
		this(entity, false);
	}
	
	/**
	 * @param entity
	 * @param onlyId
	 * 	Se for true, será carregado apenas o id da entidade.
	 */
	public BaseEntityResourceImpl(final E entity, boolean onlyId) {
		if (entity != null) {
			setId(entity.getId());
			if (!onlyId) {
				loadFromEntity(entity);
			}
		}
		//loadExtraFromEntity(entity);
	}
	
	/**
	 * Responsável por carregar os dados da entidade no resource. Deve ser implementado na classe concreta.
	 * 
	 * @param entity
	 * @param resource
	 */
	public void loadFromEntity(E entity) {
		
	}
	
	/**
	 * Deve ser sobrescrito na classe concreta para inicializar campos extras.
	 * @param entity
	 */
	public void loadExtraFromEntity(E entity) {
	}

	/* (non-Javadoc)
	 * @see br.jus.framework.service.domain.api.RecursoBase#getId()
	 */
	@Override
	@XmlID
	public PK getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see br.jus.framework.service.domain.api.RecursoBase#setId(java.io.Serializable)
	 */
	@Override
	public void setId(PK id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see br.jus.framework.service.api.BaseEntityResource#getVersion()
	 */
	public Long getVersion() {
		return version;
	}

	/* (non-Javadoc)
	 * @see br.jus.framework.service.api.BaseEntityResource#setVersion(java.lang.Long)
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

}
