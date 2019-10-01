package br.com.framework.piloto.core.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import br.com.framework.domain.api.BaseEntityAudited;
import br.com.framework.domain.enums.Status;

/**
 * Superclasse {@link MappedSuperclass} que server de base para todas as
 * entidades que necessitam suportar exclusão lógica.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 * 
 */
@MappedSuperclass
public abstract class BaseEntityAuditedImpl<PK extends Serializable> extends BaseEntityImpl<PK>
		implements BaseEntityAudited<PK> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1954018514051702076L;

	private LocalDateTime creationDate;
	private LocalDateTime lastModificationDate;
	private String creationAuthor;
	private String lastModificationAuthor;
	private Status status;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.framework.domain.api.BaseEntityAudited#getStatus()
	 */
	@Override
	@Column(name = "HABILITADO", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	public Status getStatus() {
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.framework.domain.api.BaseEntityAudited#setStatus(br.com.
	 * framework.dominio.api.EntidadeBaseAuditada.Status)
	 */
	@Override
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public BaseEntityAudited<PK> status(Status status) {
		setStatus(status);
		return this;
	}

	@Override
	@Column(name = "CREATION_DATE", nullable = false)
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	

	@Override
	public BaseEntityAudited<PK> creationDate(LocalDateTime createDate) {
		setCreationDate(createDate);
		return this;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntityAudited#setLastModificationDate(java.time.LocalDateTime)
	 */
	@Override
	public void setLastModificationDate(LocalDateTime lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntityAudited#getLastModificationDate()
	 */
	@Override
	@Column(name = "LASTMODIF_DATE", nullable = false)
	public LocalDateTime getLastModificationDate() {
		return lastModificationDate;
	}

	@Override
	public BaseEntityAudited<PK> lastModificationDate(LocalDateTime lastModifiedDate) {
		setLastModificationDate(lastModifiedDate);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntityAudited#getLastModificationAuthor()
	 */
	@Column(name = "CREATION_AUTHOR", length = 255, nullable = true)
	public String getCreationAuthor() {
		return creationAuthor;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntityAudited#setCreationAuthor(java.lang.String)
	 */
	public void setCreationAuthor(String creationAuthor) {
		this.creationAuthor = creationAuthor;
	}

	@Override
	public BaseEntityAudited<PK> creationAuthor(String creationAuthor) {
		setCreationAuthor(creationAuthor);
		return this;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntityAudited#getLastModificationAuthor()
	 */
	@Column(name = "LASTMODIF_AUTHOR", length = 255, nullable = true)
	public String getLastModificationAuthor() {
		return lastModificationAuthor;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntityAudited#setLastModificationAuthor(java.lang.String)
	 */
	public void setLastModificationAuthor(String lastModificationAuthor) {
		this.lastModificationAuthor = lastModificationAuthor;
	}

	@Override
	public BaseEntityAudited<PK> lastModificationAuthor(String lastModificationAuthor) {
		setLastModificationAuthor(lastModificationAuthor);
		return this;
	}
	
	/**
	 * Executa antes de inserir o registro.
	 */
	@PrePersist
	public void prePersist() {
		preUpdate();
	}

	/**
	 * Executa antes de editar o registro.
	 */
	@PreUpdate
	public void preUpdate() {
		LocalDateTime now = LocalDateTime.now();
		if (getCreationDate() == null) {
			setCreationDate(now);
		}
		setLastModificationDate(now);
		if (getStatus() == null) {
			setStatus(Status.ACTIVE);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.framework.domain.api.BaseEntityAudited#isAtivo()
	 */
	@Override
	@Transient
	public boolean isActive() {
		return getStatus() != null && getStatus().equals(Status.ACTIVE);
	}

}
