/**
 * 
 */
package br.jus.framework.search.impl;

import java.io.Serializable;

/**
 * Restrição utilizada como critério de pesquisa.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public class Restriction implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5673283638174599388L;
	
	private String field;
	private Operator operator;
	private Object value;
	
	/**
	 * 
	 */
	public Restriction() {
	}

	/**
	 * @param field
	 * @param operator
	 * @param value
	 */
	public Restriction(String field,
			Operator operator,
			Object value) {
		super();
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	/**
	 * @return
	 */
	public String getField() {
		return field;
	}

	/**
	 * @return
	 */
	public Operator getOperator() {
		return operator;
	}

	/**
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

}
