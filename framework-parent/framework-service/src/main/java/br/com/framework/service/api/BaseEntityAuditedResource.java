/**
 * 
 */
package br.com.framework.service.api;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.framework.domain.api.BaseEntityAudited;
import br.com.framework.domain.enums.Status;

/**
 * Interface base para os resources de uma {@link BaseEntityAudited}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária.
 * @param <E> Tipo da entidade.
 */
public interface BaseEntityAuditedResource<PK extends Serializable, E extends BaseEntityAudited<PK>> extends BaseEntityResource<PK, E> {

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
	
}
