package br.com.framework.view.datamodel;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import br.com.framework.domain.api.BaseEntity;

public class LazySorter<E extends BaseEntity<?>> implements Comparator<E> {

	private Class<E> clazz;
	private String sortField;
	private SortOrder sortOrder;
	
	public LazySorter(Class<E> clazz, String sortField, SortOrder sortOrder) {
		this.clazz = clazz;
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int compare(E entidade1, E entidade2) {
		try {
			Object value1 = clazz.getField(this.sortField).get(entidade1);
			Object value2 = clazz.getField(this.sortField).get(entidade2);

			int value = ((Comparable) value1).compareTo(value2);

			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}