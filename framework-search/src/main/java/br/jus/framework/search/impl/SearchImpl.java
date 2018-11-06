/**
 * 
 */
package br.jus.framework.search.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NonUniqueResultException;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.jus.framework.search.api.Search;
import br.jus.framework.search.api.SearchResult;
import br.jus.framework.search.api.SearchUniqueResult;
import br.jus.framework.search.exception.SearchException;
import br.jus.framework.search.util.SearchUtil;

/**
 * Classe abstrata que implementa a interface {@link Search} para componentes de consulta.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
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
	
	public static final String[] DATE_FORMATS = new String[] {
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
			"yyyy-MM-dd'T'HH:mm:ss.SSSZ",
			"yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
			"yyyy-MM-dd'T'HH:mm:ss.SSS",
			"yyyy-MM-dd'T'HH:mm:ss",
			"yyyy-MM-dd'T'HH:mm",
			"yyyy-MM-dd'T'HH",
			"yyyy-MM-dd",
			"HH:mm:ss.SSS'Z'",
			"HH:mm:ss.SSSZ",
			"HH:mm:ss.SSSXXX",
			"HH:mm:ss.SSS",
			"HH:mm:ss",
			"HH:mm",
			"HH"};
	
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
			Operator operator, Object value) throws SearchException,
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
			Restriction restriction) throws SearchException,
			NonUniqueResultException {
		List<Restriction> restrictions = new ArrayList<Restriction>(1);
		restrictions.add(restriction);
		return findUniqueByRestrictions(restrictions);
	}

	@Override
	public SearchUniqueResult<Doc> findUniqueByRestrictions(
			List<Restriction> restrictions) throws SearchException,
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
	 * Efetuar o parser de uma string data.
	 * 
	 * @param dateStr
	 * @return
	 */
	protected Date parseDate(String dateStr) {
		Date dateValue = null;
		try {
			dateValue = DateUtils.parseDate(dateStr, DATE_FORMATS);
		} catch (ParseException e) {
			logger.error("Erro ao realizar parser de data: " + dateStr, e);
		}
		return dateValue;
	}

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
