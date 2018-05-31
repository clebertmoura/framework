/**
 * 
 */
package br.com.framework.service.impl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.service.api.BaseEntityResource;
import br.com.framework.service.api.BaseResource;

/**
 * Implementação da interface {@link BaseResource}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária.
 * @param <E> Tipo da entidade.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public abstract class BaseEntityResourceImpl<PK extends Serializable, E extends BaseEntity<PK>> extends BaseResourceImpl implements BaseEntityResource<PK, E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PK id;
	private Long versaoEntidade;
	
	protected boolean onlyId = false;

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
		this.onlyId = onlyId;
		if (entity != null) {
			setId(entity.getId());
			if (!onlyId) {
				setVersaoEntidade(entity.getVersaoEntidade());
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
	public void loadFromEntity(E entity){
		setId(entity.getId());
		setVersaoEntidade(entity.getVersaoEntidade());
	}
	
	/**
	 * Deve ser sobrescrito na classe concreta para inicializar campos extras.
	 * @param entity
	 */
	public void loadExtraFromEntity(E entity) {
	}

	/* (non-Javadoc)
	 * @see br.com.framework.service.domain.api.RecursoBase#getId()
	 */
	@Override
	@XmlTransient
	public PK getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.service.domain.api.RecursoBase#setId(java.io.Serializable)
	 */
	@Override
	public void setId(PK id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.service.api.BaseEntityResource#getVersaoEntidade()
	 */
	public Long getVersaoEntidade() {
		return versaoEntidade;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.service.api.BaseEntityResource#setVersaoEntidade(java.lang.Long)
	 */
	public void setVersaoEntidade(Long versaoEntidade) {
		this.versaoEntidade = versaoEntidade;
	}

}
