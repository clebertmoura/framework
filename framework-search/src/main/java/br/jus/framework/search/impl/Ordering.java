/**
 * 
 */
package br.jus.framework.search.impl;

import java.io.Serializable;

/**
 * Ordenação.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public class Ordering implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3789669735932135446L;

	public static enum Order {
		ASC, DESC;
	}
	
	private String field;
	private Order order;

	/**
	 * 
	 */
	public Ordering() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param field
	 * @param order
	 */
	public Ordering(String field, Order order) {
		super();
		this.field = field;
		this.order = order;
	}

	public String getField() {
		return field;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setField(String field) {
		this.field = field;
	}

	/* (non-Javadoc)
	 * @see br.jus.framework.search.search.api.Ordering#isAscending()
	 */
	public boolean isAscending() {
		return getOrder().equals(Order.ASC);
	}

	/**
	 * Inverte a ordem.
	 * 
	 * @return
	 */
	public Ordering inverse() {
		if (isAscending()) {
			setOrder(Order.DESC);
		} else {
			setOrder(Order.ASC);
		}
		return this;
	}

}
