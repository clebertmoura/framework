package br.com.framework.search.impl;

/**
 * Representa um filtro a ser aplicado na consulta.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class FilterMetadata {
	
	private Object value;
	private Operator operator;

	public FilterMetadata() {
		super();
	}

	public FilterMetadata(Object value, Operator operator) {
		super();
		this.value = value;
		this.operator = operator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

}