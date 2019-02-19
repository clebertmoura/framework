package br.com.framework.model.manager.sync;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.framework.domain.api.BaseEntity;

/**
 * DTO utilizado para resposta de sincronização das entidades.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class EntitySyncResponse<PK extends Serializable, E extends BaseEntity<PK>> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime lastSync;
	private SyncPageResponse<E> inserted;
	private SyncPageResponse<E> updated;
	private SyncPageResponse<PK> deleted;
	private SyncPageResponse<PK> expired;
	
	public EntitySyncResponse() {
	}

	public LocalDateTime getLastSync() {
		return lastSync;
	}

	public void setLastSync(LocalDateTime lastSync) {
		this.lastSync = lastSync;
	}

	public SyncPageResponse<E> getInserted() {
		return inserted;
	}

	public void setInserted(SyncPageResponse<E> inserted) {
		this.inserted = inserted;
	}

	public SyncPageResponse<E> getUpdated() {
		return updated;
	}

	public void setUpdated(SyncPageResponse<E> updated) {
		this.updated = updated;
	}

	public SyncPageResponse<PK> getDeleted() {
		return deleted;
	}

	public void setDeleted(SyncPageResponse<PK> deleted) {
		this.deleted = deleted;
	}

	public SyncPageResponse<PK> getExpired() {
		return expired;
	}

	public void setExpired(SyncPageResponse<PK> expired) {
		this.expired = expired;
	}

}
