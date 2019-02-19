package br.com.framework.domain.api;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.framework.domain.enums.Status;

/**
 * Interface base para as entidades de domínio auditáveis e com suporte a exclusão lógica. 
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária.
 */
public interface BaseEntityAudited<PK extends Serializable> extends BaseEntity<PK> {
	/**
	 * @return
	 */
	public abstract LocalDateTime getCreateDate();
	/**
	 * @param createDate
	 */
	public abstract void setCreateDate(LocalDateTime createDate);
	
	/**
	 * @return
	 */
	public abstract LocalDateTime getLastModifiedDate();
	/**
	 * @param lastModifiedDate
	 */
	public abstract void setLastModifiedDate(LocalDateTime lastModifiedDate);
	
	/**
	 * @return
	 */
	public abstract String getUserLogin();
	/**
	 * @param userLogin
	 */
	public abstract void setUserLogin(String userLogin);
	
	/**
	 * @return
	 */
	public abstract Status getStatus();
	/**
	 * @param status
	 */
	public abstract void setStatus(Status status);
	
	/**
	 * @return
	 */
	public abstract boolean isActive();

}