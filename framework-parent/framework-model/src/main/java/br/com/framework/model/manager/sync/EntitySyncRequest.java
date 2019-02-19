package br.com.framework.model.manager.sync;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.framework.search.impl.Ordering;
import br.com.framework.search.impl.Restriction;

/**
 * DTO utilizado para requisição de sincronização das entidades.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class EntitySyncRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime lastSync;
	private List<Restriction> restrictions = new ArrayList<Restriction>();
	private Integer first = -1;
	private Integer max = -1;
	private List<Ordering> orderings = new ArrayList<Ordering>();

	public EntitySyncRequest() {
		// TODO Auto-generated constructor stub
	}
	
	public EntitySyncRequest(Integer first, Integer max) {
		super();
		this.first = first;
		this.max = max;
	}

	public EntitySyncRequest(LocalDateTime lastSync, Integer first, Integer max) {
		super();
		this.lastSync = lastSync;
		this.first = first;
		this.max = max;
	}
	
	public EntitySyncRequest(LocalDateTime lastSync) {
		super();
		this.lastSync = lastSync;
	}

	public LocalDateTime getLastSync() {
		return lastSync;
	}

	public void setLastSync(LocalDateTime lastSync) {
		this.lastSync = lastSync;
	}

	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public List<Ordering> getOrderings() {
		return orderings;
	}

	public void setOrderings(List<Ordering> orderings) {
		this.orderings = orderings;
	}

	public List<Restriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

}
