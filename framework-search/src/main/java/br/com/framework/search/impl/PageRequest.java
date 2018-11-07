/**
 * 
 */
package br.com.framework.search.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.framework.search.impl.Ordering.Order;

/**
 * Representa a requisição a uma página de resultados.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class PageRequest {
	/**
     * First row offset.
     */
    private int first;
    /**
     * Number of rows per page.
     */
    private int rows;
    /**
     * The sorting field
     */
    private String sortField;
    /**
     * The sorting order 
     */
    private Order sortOrder;
    /**
     * Lista campos para ordenação.
     */
    private List<SortMeta> multiSortMeta = new ArrayList<>();
    /**
     * Indica o operando a ser utilizado nos filtros, por default utiliza o: AND
     */
    private Boolean andOperand = true;
    /**
     * Lista dos filtros
     */
    private Map<String, FilterMetadata> filters = new HashMap<>();
    /**
     * Filtro geral 
     */
    private String globalFilter;
    /**
     * Nome do EntityGraph a ser utilizado para carregamento 
     */
    private String entityGraph;
    /**
     * A profundidade da serialização dos relacionamentos.
     */
    private Integer depth;

	/**
	 * 
	 */
	public PageRequest() {
	}

	public PageRequest(int first, int rows, String sortField, Order sortOrder, List<SortMeta> multiSortMeta,
			Map<String, FilterMetadata> filters, String globalFilter) {
		super();
		this.first = first;
		this.rows = rows;
		this.sortField = sortField;
		this.sortOrder = sortOrder;
		this.multiSortMeta = multiSortMeta;
		this.filters = filters;
		this.globalFilter = globalFilter;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public Order getSortOrder() {
		return sortOrder != null ? sortOrder : Order.ASC;
	}

	public void setSortOrder(Order sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<SortMeta> getMultiSortMeta() {
		if (multiSortMeta == null) {
			multiSortMeta = new ArrayList<>();
		}
		return multiSortMeta;
	}

	public void setMultiSortMeta(List<SortMeta> multiSortMeta) {
		this.multiSortMeta = multiSortMeta;
	}

	public Map<String, FilterMetadata> getFilters() {
		if (filters == null) {
			filters = new HashMap<>();
		}
		return filters;
	}

	public void setFilters(Map<String, FilterMetadata> filters) {
		this.filters = filters;
	}

	public String getGlobalFilter() {
		return globalFilter;
	}

	public void setGlobalFilter(String globalFilter) {
		this.globalFilter = globalFilter;
	}

	public Boolean isAndOperand() {
		return andOperand != null ? andOperand : true;
	}

	public void setAndOperand(Boolean isAndOperand) {
		this.andOperand = isAndOperand;
	}

	public Boolean getAndOperand() {
		return andOperand;
	}

	public String getEntityGraph() {
		return entityGraph;
	}

	public void setEntityGraph(String entityGraph) {
		this.entityGraph = entityGraph;
	}

	public Integer getDepth() {
		return depth != null ? depth : 0;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

}
