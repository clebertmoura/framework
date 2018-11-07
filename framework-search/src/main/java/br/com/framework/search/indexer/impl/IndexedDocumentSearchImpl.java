/**
 * 
 */
package br.com.framework.search.indexer.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.search.api.SearchResult;
import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.search.exception.SearchException;
import br.com.framework.search.impl.Operator;
import br.com.framework.search.impl.Ordering;
import br.com.framework.search.impl.Restriction;
import br.com.framework.search.impl.SearchImpl;
import br.com.framework.search.indexer.api.IndexedDocument;
import br.com.framework.search.indexer.api.IndexedDocumentSearch;
import br.com.framework.search.indexer.api.service.IndexerService;
import br.com.framework.search.util.SearchUtil;
import br.com.framework.util.reflection.ReflectionUtils;

/**
 * Classe abstrata que implementa a interface {@link IndexedDocumentSearch} para componentes de consulta indexada a documentos.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <DocId> Classe do Tipo do identificador do documento.
 * @param <Doc> Classe do Tipo do documento.
 */
public abstract class IndexedDocumentSearchImpl<DocId extends Serializable, Doc extends IndexedDocument<DocId>>
	extends SearchImpl<DocId, Doc> implements IndexedDocumentSearch<DocId, Doc> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FIELD_VERSION = "_version_";
	public static final String FIELD_UUID = "uuid";

	private static final String REGEX_ENTITYCLASS_SEPARATOR = "[|]";
	
	protected IndexerService indexerService;

	/**
	 * 
	 */
	public IndexedDocumentSearchImpl(Class<Doc> entidadeClass) {
		super(entidadeClass);
	}
	
	/* (non-Javadoc)
	 * @see br.com.framework.search.search.indexer.api.IndexedDocumentSearchable#findByUUID(java.lang.String)
	 */
	@Override
	public SearchUniqueResult<Doc> findByUUID(String uuid)
			throws SearchException {
		return findUniqueByField(FIELD_UUID, Operator.EQ, uuid);
	}
	
	/**
	 * Retorna true para os campos que devem ser desconsiderados na montagem do objeto.
	 * 
	 * @param fieldName
	 * @return
	 */
	protected boolean bypassField(String fieldName) {
		boolean result = false;
		if (fieldName.equals(FIELD_VERSION)) {
			result = true;
		}
		return result;
	}
	
	/**
	 * @param fieldName
	 * @return
	 */
	protected boolean isIdFieldLongType(String fieldName) {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SearchResult<Doc> findByRestrictions(
			List<Restriction> restrictions, int firstResult, int maxResults,
			Ordering... orderings) throws SearchException {
		long start = System.currentTimeMillis();
		setDefaultRestrictons(restrictions);
		QueryResponse response = queryByRestrictions(restrictions, firstResult, maxResults, orderings);
		List<Doc> resultList = new ArrayList<Doc>();
		if (response != null) {
			SolrDocumentList results = response.getResults();
			if (!results.isEmpty()) {
				for (SolrDocument doc : results) {
					Doc instance = null;
					try {
						instance = getDocumentClass().newInstance();
					} catch (Exception e) {
						logger.error(String.format("Erro ao instanciar class: %s", getDocumentClass().getName()), e);
						throw new SearchException(String.format("Erro ao instanciar class: %s", getDocumentClass().getName()), e);
					}
					Collection<String> fieldNames = doc.getFieldNames();
					for (String fieldName : fieldNames) {
						if (bypassField(fieldName)) {
							continue;
						}
						Object fieldValue = doc.getFieldValue(fieldName);
						if (fieldValue != null) {
							Field field = null;
							try {
								field = ReflectionUtils.getField(fieldName, getDocumentClass());
								field.setAccessible(true);
							} catch (Exception e) {
								logger.error(String.format("Erro ao acessar atributo %s de %s", 
										fieldName, getDocumentClass().getName()), e);
							}
							if (field != null) {
								try {
									if (Date.class.isAssignableFrom(field.getType()) || java.sql.Date.class.isAssignableFrom(field.getType()) || Timestamp.class.isAssignableFrom(field.getType())
											|| Time.class.isAssignableFrom(field.getType())) {
										Object value = null;
										if (List.class.isAssignableFrom(fieldValue.getClass())) {
											value = ((List<?>)fieldValue).iterator().next();
										} else {
											value = fieldValue;
										}
										Date dateValue = DateUtils.parseDate(value.toString(), DATE_FORMATS);
										if (Date.class.isAssignableFrom(field.getType())) {
											field.set(instance, dateValue);
										} else if (java.sql.Date.class.isAssignableFrom(field.getType())){
											field.set(instance, new java.sql.Date(dateValue.getTime()));
										} else if (Timestamp.class.isAssignableFrom(field.getType())){
											field.set(instance, new java.sql.Timestamp(dateValue.getTime()));
										} else {
											field.set(instance, new java.sql.Time(dateValue.getTime()));
										}
									} else if (BaseEntity.class.isAssignableFrom(field.getType())) {
										try {
											String[] strings = fieldValue.toString().split(REGEX_ENTITYCLASS_SEPARATOR);
											Class<?> eClass = Class.forName(strings[0]);
											BaseEntity<Long> newInstance = (BaseEntity<Long>) eClass.newInstance();
											newInstance.setId(Long.valueOf(strings[1]));
										} catch(Exception e) {
											logger.error(String.format("A atributo %s de %s não foi setado.", 
													field.getName(), getDocumentClass().getName()), e);
										}
									} else {
										if (isIdFieldLongType(fieldName)) {
											try {
												field.set(instance, Long.parseLong(fieldValue.toString()));
											} catch(NumberFormatException e) {
												field.set(instance, fieldValue);
											}
										} else {
											field.set(instance, fieldValue);
										}
									}
								} catch (Exception e) {
									logger.error(String.format("Erro ao setar o atributo %s de %s", 
											field.getName(), getDocumentClass().getName()), e);
								}
							}
						}
					}
					if (instance != null) {
						resultList.add(instance);
					}
				}
			}
		}
		return SearchUtil.instance().searchResult(getDocumentClass(), resultList, System.currentTimeMillis() - start);
	}

	/**
	 * Adiciona {@link Restriction} por padrão na lista de {@link Restriction}.
	 * 
	 * @param restrictions
	 */
	protected void setDefaultRestrictons(List<Restriction> restrictions) {
	}

	/* (non-Javadoc)
	 * @see br.com.framework.search.search.api.Searchable#getRecordCountFindByRestrictions(java.util.List)
	 */
	@Override
	public SearchUniqueResult<Long> getCountFindByRestrictions(
			List<Restriction> restrictions) throws SearchException {
		long start = System.currentTimeMillis();
		setDefaultRestrictons(restrictions);
		QueryResponse response = queryByRestrictions(restrictions, -1, -1);
		Long result = 0L;
		if (response != null) {
			SolrDocumentList results = response.getResults();
			if (!results.isEmpty()) {
				result = (long) results.size();
			}
		}
		return SearchUtil.instance().searchUniqueResult(Long.class, result, System.currentTimeMillis() - start);
	}


	/**
	 * 
	 * @param restrictions
	 * @param firstResult
	 * @param maxResults
	 * @param orderings
	 * @return
	 * @throws SearchException
	 */
	protected QueryResponse queryByRestrictions(List<Restriction> restrictions,
			int firstResult, int maxResults, Ordering... orderings) throws SearchException{
		StringBuffer sb = new StringBuffer();
		if (restrictions != null && !restrictions.isEmpty()) {
			for (int i = 0; i < restrictions.size(); i++) {
				Restriction restric = restrictions.get(i);
				if (restric.getOperator().equals(Operator.NE)) {
					sb.append("-");
				}
				sb.append(restric.getField());
				sb.append(":");
				switch (restric.getOperator()) {
					case EQ:
					case EI:
						sb.append(restric.getValue().toString());
						break;
					case NE:
						sb.append(restric.getValue().toString());
						break;
					case GE:
					case GT:
						sb.append(":[");
						sb.append(restric.getValue().toString());
						sb.append(" TO *]");
						break;
					case LE:
					case LT:
						sb.append(":[* TO ");
						sb.append(restric.getValue().toString());
						sb.append("]");
						break;
					case IN:
						sb.append(":(");
						List<?> itens = (List<?>) restric.getValue();
						for (int j = 0; j < itens.size(); j++) {
							Object item = itens.get(j);
							if (BaseEntity.class.isAssignableFrom(item.getClass())) {
								sb.append(((BaseEntity<?>)item).getId());
							} else if (IndexedDocument.class.isAssignableFrom(item.getClass())) {
								sb.append(((IndexedDocument<?>)item).getUuid());
							} else {
								sb.append(item.toString());
							}
							if (j < itens.size()-1) {
								sb.append(", ");
							}
						}
						sb.append(")");
						break;
					case LI:
					case LK:
						sb.append("*");
						sb.append(restric.getValue().toString());
						sb.append("*");
						break;
				}
				if (i < restrictions.size()-1) {
					sb.append(" AND ");
				}
			}
		}
		SolrQuery query = new SolrQuery();
		query.setQuery(sb.toString());
		if (firstResult > -1 && maxResults > 0) {
			query.setStart(firstResult);
			query.setRows(maxResults);
		}
		if (orderings != null && orderings.length > 0) {
			for (Ordering ordering : orderings) {
				if (ordering != null) {
					query.addSort(ordering.getField(), ordering.isAscending() ? ORDER.asc : ORDER.desc);
				}
			}
		}
		QueryResponse response = null;
		if (isSolrServiceAvailable()) {
			try {
				response = getSolrService().getSolrServer().query(query);
			} catch (SolrServerException e) {
				logger.error(String.format("Ocorreu um erro ao realizar a consulta no serviço de índices. %s", e.getMessage()), e);
				throw new SearchException(e.getMessage(), e);
			}
		} else {
			logger.error("O serviço de indices está indisponível. Não foi possível realizar a consulta.");
			throw new SearchException("Serviço de índices indisponível!");
		}
		return response;
	}

	/**
	 * Verifica se o {@link IndexerService} está disponível
	 * @return
	 */
	private boolean isSolrServiceAvailable() {
		return getSolrService() != null && getSolrService().isServiceActive();
	}
	

	/**
	 * @return the indexerService
	 */
	protected IndexerService getSolrService() {
		return indexerService;
	}

	/**
	 * @param indexerService the indexerService to set
	 */
	protected void setSolrService(IndexerService indexerService) {
		this.indexerService = indexerService;
	}

}
