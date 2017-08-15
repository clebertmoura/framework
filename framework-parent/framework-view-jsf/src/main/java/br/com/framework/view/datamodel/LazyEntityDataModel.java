package br.com.framework.view.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.search.api.Search;
import br.com.framework.search.impl.Operator;
import br.com.framework.search.impl.Ordering;
import br.com.framework.search.impl.Restriction;
import br.com.framework.search.impl.Ordering.Order;
import br.com.framework.search.util.SearchUtil;

public class LazyEntityDataModel<E extends BaseEntity<? extends Serializable>> extends
		LazyDataModel<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected List<E> datasource;
	
	protected Search<? extends Serializable, E> search;

	/**
	 * @param negocio
	 */
	public LazyEntityDataModel(Search<? extends Serializable, E> searchable) {
		this.search = searchable;
		this.datasource = new ArrayList<E>();
		setWrappedData(this.datasource);
	}

	@Override
	public E getRowData(String rowKey) {
		for (E car : datasource) {
			if (car.getId().equals(rowKey))
				return car;
		}
		return null;
	}

	@Override
	public Object getRowKey(E entidade) {
		Serializable id = entidade.getId();
		return id != null ? id : entidade.hashCode();
	}
	
	/**
	 * @param filters
	 * @return
	 */
	protected List<Restriction> getRestricoesDosFiltros(Map<String, Object> filters) {
		List<Restriction> restrictions = new ArrayList<Restriction>();
		for (Entry<String, Object> entry : filters.entrySet()) {
			restrictions.add(SearchUtil.instance().restriction(entry.getKey(), Operator.LI, entry.getValue()));
		}
		return restrictions;
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
	 */
	@Override
	public List<E> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		datasource.clear();
		List<Restriction> restricoes = getRestricoesDosFiltros(filters);
		Long count = search.getCountFindByRestrictions(restricoes).getUniqueResult();
		this.setRowCount(count.intValue());
		List<Ordering> orderings = new ArrayList<Ordering>();
		if (sortField != null && sortOrder != null) {
			orderings.add(SearchUtil.instance().order(sortField, sortOrder.equals(SortOrder.ASCENDING) ? Order.ASC : Order.DESC));
		}
		datasource.addAll((Collection<? extends E>) 
				search.findByRestrictions(restricoes, first, pageSize, orderings.toArray(new Ordering[0])).getResults());
		return datasource;
	}
	
	@Override
	public List<E> load(int first, int pageSize, List<SortMeta> multiSortMeta,
			Map<String, Object> filters) {
		datasource.clear();
		List<Restriction> restrictions = getRestricoesDosFiltros(filters);
		Long count = search.getCountFindByRestrictions(restrictions).getUniqueResult();
		this.setRowCount(count.intValue());
		List<Ordering> orderings = new ArrayList<Ordering>();
		if (multiSortMeta != null && !multiSortMeta.isEmpty()) {
			for (SortMeta sortMeta : multiSortMeta) {
				orderings.add(SearchUtil.instance().order(sortMeta.getSortField(), sortMeta.getSortOrder().equals(SortOrder.ASCENDING) ? Order.ASC : Order.DESC));
			}
		}
		datasource.addAll((Collection<? extends E>) 
				search.findByRestrictions(restrictions, first, pageSize, orderings.toArray(new Ordering[0])).getResults());
		return datasource;
	}

}