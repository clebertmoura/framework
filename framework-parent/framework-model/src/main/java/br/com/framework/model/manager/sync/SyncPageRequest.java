package br.com.framework.model.manager.sync;

import java.io.Serializable;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class SyncPageRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer first = -1;
	private Integer max = -1;

	public SyncPageRequest() {
		// TODO Auto-generated constructor stub
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

}
