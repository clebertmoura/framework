package br.com.framework.pilotojee7.core.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
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

	private LocalDateTime createDate;
	private LocalDateTime lastModifiedDate;
	private String userLogin;
	private Status status;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.framework.domain.api.BaseEntityAudited#getStatus()
	 */
	@Override
	@Column(name = "FL_HABILITADO", nullable = false)
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
	@Column(name = "DH_CRIACAO", nullable = false)
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntityAudited#setLastModifiedDate(java.util.Date)
	 */
	@Override
	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.framework.domain.api.BaseEntityAudited#getDataModificacao()
	 */
	@Override
	@Column(name = "DH_MODIFICACAO", nullable = false)
	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.framework.domain.api.BaseEntityAudited#getUsuarioLogin()
	 */
	@Column(name = "NM_LOGIN", length = 255, nullable = true)
	public String getUserLogin() {
		return userLogin;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.domain.api.BaseEntityAudited#setUserLogin(java.lang.String)
	 */
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
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
		if (getCreateDate() == null) {
			setCreateDate(now);
		}
		setLastModifiedDate(now);
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
