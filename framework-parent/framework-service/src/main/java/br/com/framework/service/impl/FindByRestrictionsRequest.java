package br.com.framework.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.framework.search.impl.Ordering;
import br.com.framework.search.impl.Restriction;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@XmlRootElement
public class FindByRestrictionsRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Restriction> restrictions = new ArrayList<Restriction>();
	private Integer first;
	private Integer max;
	private List<Ordering> orderings = new ArrayList<Ordering>();
	private String entityGraphName;

	/**
	 * 
	 */
	public FindByRestrictionsRequest() {
	}

	/**
	 * @param restrictions
	 * @param first
	 * @param max
	 * @param orderings
	 */
	public FindByRestrictionsRequest(List<Restriction> restrictions,
			Integer first, Integer max, List<Ordering> orderings) {
		super();
		this.restrictions = restrictions;
		this.first = first;
		this.max = max;
		this.orderings = orderings;
	}

	/**
	 * @return
	 */
	public List<Restriction> getRestrictions() {
		return restrictions;
	}

	/**
	 * @param restrictions the restrictions to set
	 */
	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	/**
	 * @return
	 */
	public Integer getFirst() {
		return first;
	}

	/**
	 * @return
	 */
	public Integer getMax() {
		return max;
	}


	/**
	 * @return
	 */
	public List<Ordering> getOrderings() {
		return orderings;
	}

	/**
	 * @param first the first to set
	 */
	public void setFirst(Integer first) {
		this.first = first;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(Integer max) {
		this.max = max;
	}

	/**
	 * @param orderings the orderings to set
	 */
	public void setOrderings(List<Ordering> orderings) {
		this.orderings = orderings;
	}

	public String getEntityGraphName() {
		return entityGraphName;
	}

	public void setEntityGraphName(String entityGraphName) {
		this.entityGraphName = entityGraphName;
	}


}
