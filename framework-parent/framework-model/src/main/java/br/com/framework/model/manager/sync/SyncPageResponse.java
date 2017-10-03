package br.com.framework.model.manager.sync;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class SyncPageResponse<R extends Serializable> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long countRegisters = -1L;
	private List<R> registers;

	public SyncPageResponse() {
	}

	public SyncPageResponse(Long countRegisters, List<R> registers) {
		super();
		this.countRegisters = countRegisters;
		this.registers = registers;
	}

	public Long getCountRegisters() {
		return countRegisters;
	}

	public void setCountRegisters(Long countRegisters) {
		this.countRegisters = countRegisters;
	}

	public List<R> getRegisters() {
		if (registers == null) {
			registers = new ArrayList<>();
		}
		return registers;
	}

	public void setRegisters(List<R> registers) {
		this.registers = registers;
	}


}
