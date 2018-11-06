package br.jus.framework.search.impl;

import br.jus.framework.search.impl.Ordering.Order;

/**
 * Representa um campo para ordenação.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public class SortMeta {
	
	private String field;
	private Order order;
	
	public SortMeta() {
		super();
	}

	public SortMeta(String field, Order order) {
		super();
		this.field = field;
		this.order = order;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Order getOrder() {
		return order != null ? order : Order.ASC;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}