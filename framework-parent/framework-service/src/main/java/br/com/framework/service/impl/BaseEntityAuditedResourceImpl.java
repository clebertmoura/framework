/**
 * 
 */
package br.com.framework.service.impl;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.domain.api.BaseEntityAudited;
import br.com.framework.domain.enums.Status;
import br.com.framework.service.api.BaseEntityAuditedResource;
import br.com.framework.service.api.BaseResource;

/**
 * Implementação da interface {@link BaseResource}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK>
 * @param <E>
 */
@XmlRootElement
public abstract class BaseEntityAuditedResourceImpl<PK extends Serializable, E extends BaseEntityAudited<PK>> extends BaseEntityResourceImpl<PK, E> implements BaseEntityAuditedResource<PK, E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime createDate;
	private LocalDateTime lastModifiedDate;
	private String userLogin;
	private Status status;

	/**
	 * 
	 */
	public BaseEntityAuditedResourceImpl() {
	}
	
	/**
	 * @param entity
	 */
	public BaseEntityAuditedResourceImpl(final E entity) {
		this(entity, false);
	}
	
	/**
	 * @param entity
	 * @param onlyId
	 * 	Se for true, será carregado apenas o id da entidade.
	 */
	public BaseEntityAuditedResourceImpl(final E entity, boolean onlyId) {
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
	public void loadFromEntity(E entity){
		setCreateDate(entity.getCreateDate());
		setLastModifiedDate(entity.getLastModifiedDate());
		setUserLogin(entity.getUserLogin());
		setStatus(entity.getStatus());
	}
	
	/**
	 * Deve ser sobrescrito na classe concreta para inicializar campos extras.
	 * @param entity
	 */
	public void loadExtraFromEntity(E entity) {
	}

	/**
	 * @return the createDate
	 */
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * @return the userLogin
	 */
	public String getUserLogin() {
		return userLogin;
	}

	/**
	 * @param userLogin the userLogin to set
	 */
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	
}
