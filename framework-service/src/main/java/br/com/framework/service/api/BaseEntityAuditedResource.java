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
	public abstract LocalDateTime getCreationDate();
	/**
	 * @param creationDate
	 */
	public abstract void setCreationDate(LocalDateTime creationDate);
	
	/**
	 * @return
	 */
	public abstract LocalDateTime getLastModificationDate();
	/**
	 * @param lastModificationDate
	 */
	public abstract void setLastModificationDate(LocalDateTime lastModificationDate);
	
	/**
	 * @return
	 */
	public abstract String getCreationAuthor();
	/**
	 * @param creationAuthor
	 */
	public abstract void setCreationAuthor(String creationAuthor);

	/**
	 * @return
	 */
	public abstract String getLastModificationAuthor();
	/**
	 * @param lastModificationAuthor
	 */
	public abstract void setLastModificationAuthor(String lastModificationAuthor);
	
	/**
	 * @return
	 */
	public abstract Status getStatus();
	/**
	 * @param status
	 */
	public abstract void setStatus(Status status);
	
}
