/**
 * 
 */
package br.com.framework.search.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.search.api.Search;
import br.com.framework.search.api.SearchResult;
import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.search.exception.SearchException;
import br.com.framework.search.util.SearchUtil;

/**
 * Classe abstrata que implementa a interface {@link Search} para componentes de consulta.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <DocId> Classe do Tipo do identificador do documento.
 * @param <Doc> Classe do Tipo do documento.
 */
public abstract class SearchImpl<DocId extends Serializable, Doc extends Serializable> implements Search<DocId, Doc> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected Class<Doc> documentClass;
	
	protected SearchUtil searchUtil = SearchUtil.newInstance();
	
	/**
	 * @param documentClass
	 */
	public SearchImpl(Class<Doc> documentClass) {
		super();
		this.documentClass = documentClass;
	}
	
	/**
	 * @return the documentClass
	 */
	public Class<Doc> getDocumentClass() {
		return documentClass;
	}
	
	@Override
	public abstract SearchUniqueResult<Doc> findById(DocId id) throws SearchException;

	@Override
	public SearchResult<Doc> findByField(String field, Operator operator,
			Object value, Ordering... orderings) throws SearchException {
		return findByField(field, operator, value, -1, -1, orderings);
	}

	@Override
	public SearchResult<Doc> findByField(String field, Operator operator,
			Object value, int firstResult, int maxResults,
			Ordering... orderings) throws SearchException {
		return findByRestriction(searchUtil.restriction(field, operator, (Serializable) value), firstResult, maxResults, orderings);
	}

	@Override
	public SearchUniqueResult<Long> getCountFindByField(String field,
			Operator operator, Object value) throws SearchException {
		Restriction restriction = null;
		if (field != null && operator != null) {
			restriction = searchUtil.restriction(field, operator, (Serializable) value);
		}
		return getCountFindByRestriction(restriction);
	}
	
	@Override
	public SearchUniqueResult<Doc> findUniqueByField(String field,
			Operator operator, Object value) throws 
			NonUniqueResultException {
		return findUniqueByRestriction(searchUtil.restriction(field, operator, (Serializable) value));
	}
	
	@Override
	public SearchUniqueResult<Long> getCountFindAll()
			throws SearchException {
		return getCountFindByRestrictions(null);
	}

	@Override
	public SearchResult<Doc> findAll(Ordering... orderings)
			throws SearchException {
		return findAll(-1, -1, orderings);
	}

	@Override
	public SearchResult<Doc> findAll(int firstResult, int maxResults,
			Ordering... orderings) throws SearchException {
		return findByRestrictions(null, firstResult, maxResults, orderings);
	}

	@Override
	public SearchResult<Doc> findByRestriction(Restriction restriction,
			Ordering... orderings) throws SearchException {
		return findByRestriction(restriction, -1, -1, orderings);
	}

	@Override
	public SearchResult<Doc> findByRestriction(Restriction restriction,
			int firstResult, int maxResults, Ordering... orderings)
			throws SearchException {
		List<Restriction> restrictions = new ArrayList<Restriction>(1);
		restrictions.add(restriction);
		return findByRestrictions((List<Restriction>) restrictions, firstResult, maxResults, orderings);
	}

	@Override
	public SearchUniqueResult<Long> getCountFindByRestriction(
			Restriction restriction) throws SearchException {
		List<Restriction> restrictions = new ArrayList<Restriction>(1);
		restrictions.add(restriction);
		return getCountFindByRestrictions(restrictions);
	}

	@Override
	public SearchUniqueResult<Doc> findUniqueByRestriction(
			Restriction restriction) throws 
			NonUniqueResultException {
		List<Restriction> restrictions = new ArrayList<Restriction>(1);
		restrictions.add(restriction);
		return findUniqueByRestrictions(restrictions);
	}

	@Override
	public SearchUniqueResult<Doc> findUniqueByRestrictions(
			List<Restriction> restrictions) throws 
			NonUniqueResultException {
		SearchResult<Doc> searchResult = findByRestrictions(restrictions, -1, -1);
		if (!searchResult.getResults().isEmpty()) {
			if (searchResult.getResults().size() > 1) {
				throw new NonUniqueResultException();
			} else {
				return searchUtil.searchUniqueResult(getDocumentClass(), searchResult.getResults().get(0), searchResult.getExecutionTime());
			}
		} else {
			return searchUtil.searchUniqueResult(getDocumentClass(), null, searchResult.getExecutionTime());
		}
	}

	@Override
	public SearchResult<Doc> findByRestrictions(
			List<Restriction> restrictions, Ordering... orderings)
			throws SearchException {
		return findByRestrictions(restrictions, -1, -1, orderings);
	}

	@Override
	public abstract SearchResult<Doc> findByRestrictions(
			List<Restriction> restrictions, int firstResult, int maxResults,
			Ordering... orderings) throws SearchException;

	@Override
	public abstract SearchUniqueResult<Long> getCountFindByRestrictions(
			List<Restriction> restrictions) throws SearchException;
	
	@Override
	public abstract PageResponse<Doc> findPage(PageRequest pageRequest) throws SearchException;
	
	/**
	 * @return the logger
	 */
	protected Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	protected void setLogger(Logger logger) {
		this.logger = logger;
	}

}
