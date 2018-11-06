/**
 * 
 */
package br.jus.framework.service.impl;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import br.jus.framework.domain.api.BaseEntityAudited;
import br.jus.framework.domain.enums.Status;
import br.jus.framework.service.api.BaseEntityAuditedResource;
import br.jus.framework.service.api.BaseResource;

/**
 * Implementação da interface {@link BaseResource}
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
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
	
	private LocalDateTime creationDate;
	private LocalDateTime lastModificationDate;
	private String creationAuthor;
	private String lastModificationAuthor;
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
		setCreationDate(entity.getCreationDate());
		setLastModificationDate(entity.getLastModificationDate());
		setCreationAuthor(entity.getCreationAuthor());
		setLastModificationAuthor(entity.getLastModificationAuthor());
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
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreationDate(LocalDateTime createDate) {
		this.creationDate = createDate;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public LocalDateTime getLastModificationDate() {
		return lastModificationDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModificationDate(LocalDateTime lastModifiedDate) {
		this.lastModificationDate = lastModifiedDate;
	}

	/**
	 * @return the userLogin
	 */
	public String getCreationAuthor() {
		return creationAuthor;
	}

	/**
	 * @param userLogin the userLogin to set
	 */
	public void setCreationAuthor(String userLogin) {
		this.creationAuthor = userLogin;
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

	public String getLastModificationAuthor() {
		return lastModificationAuthor;
	}

	public void setLastModificationAuthor(String lastModificationAuthor) {
		this.lastModificationAuthor = lastModificationAuthor;
	}

	
}
