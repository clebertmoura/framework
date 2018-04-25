package br.com.framework.service.sync;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.model.manager.sync.SyncPageResponse;
import br.com.framework.service.api.BaseEntityResource;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class ResourceSyncResponse<PK extends Serializable, R extends BaseEntityResource<PK, ? extends BaseEntity<PK>>> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime lastSync;
	private SyncPageResponse<R> inserted;
	private SyncPageResponse<R> updated;
	private SyncPageResponse<PK> deleted;
	private SyncPageResponse<PK> expired;

	public ResourceSyncResponse() {
	}

	public LocalDateTime getLastSync() {
		return lastSync;
	}

	public void setLastSync(LocalDateTime lastSync) {
		this.lastSync = lastSync;
	}

	public SyncPageResponse<R> getInserted() {
		return inserted;
	}

	public void setInserted(SyncPageResponse<R> inserted) {
		this.inserted = inserted;
	}

	public SyncPageResponse<R> getUpdated() {
		return updated;
	}

	public void setUpdated(SyncPageResponse<R> updated) {
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
