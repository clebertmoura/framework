/**
 * 
 */
package br.com.framework.model.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Entity;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.domain.api.BaseEntityAudited;
import br.com.framework.domain.enums.Status;
import br.com.framework.model.dao.api.BaseDao;
import br.com.framework.model.dao.util.CriteriaBuilderHelper;
import br.com.framework.search.api.SearchResult;
import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.search.exception.SearchException;
import br.com.framework.search.impl.FilterMetadata;
import br.com.framework.search.impl.Operator;
import br.com.framework.search.impl.Ordering;
import br.com.framework.search.impl.Ordering.Order;
import br.com.framework.search.impl.PageRequest;
import br.com.framework.search.impl.PageResponse;
import br.com.framework.search.impl.Restriction;
import br.com.framework.search.impl.SearchImpl;
import br.com.framework.search.impl.SortMeta;
import br.com.framework.search.util.SearchUtil;
import br.com.framework.util.date.DateUtil;
import br.com.framework.util.reflection.ReflectionUtils;
import br.com.framework.util.validation.BooleanUtils;

/**
 * Implementação base do {@link BaseDao} das entidades de domínio.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária.
 * @param <E> Entidade de domínio.
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public abstract class BaseDaoImpl<PK extends Serializable, E extends BaseEntity<PK>> extends SearchImpl<PK, E>
		implements BaseDao<PK, E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected EntityManager entityManager;
	
	protected SearchUtil searchUtil = SearchUtil.newInstance();
	
	protected static final String FETCH_GRAPH = "javax.persistence.fetchgraph";
	
	private static final String FETCH_GRAPH_MSG = "EntityGraph %s não encontrado! O resultado será carregado de forma padrão.";
	
	protected static final String FIELD_STATUS = "status";
	protected static final String FIELD_LAST_MODIFIED_DATE = "lastModifiedDate";
	
	/**
	 * @param entityClass
	 */
	public BaseDaoImpl(Class<E> entityClass) {
		super(entityClass);
	}

	/**
	 * @return the entityClass
	 */
	public Class<E> getEntityClass() {
		return getDocumentClass();
	}
	

	/**
	 * @return the entityManager
	 */
	protected EntityManager getEntityManager() {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("EntityManager instance: %s", entityManager));
		}
		return entityManager;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * @return
	 */
	protected SearchUtil getSearchUtil() {
		return searchUtil;
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	
	public SearchUniqueResult<E> findById(PK id, String entityGraphName) {
		long start = System.currentTimeMillis();
		Map<String, Object> hints = new HashMap<>();
		if (entityGraphName != null) {
			try{
				EntityGraph graph = getEntityManager().getEntityGraph(entityGraphName);
				hints.put(FETCH_GRAPH, graph);
			} catch (IllegalArgumentException e) {
				logger.warn(String.format(FETCH_GRAPH_MSG, entityGraphName), e);
			}
		}
		E result = getEntityManager().find(getEntityClass(), id, hints);
		return getSearchUtil().searchUniqueResult(getEntityClass(), result, System.currentTimeMillis() - start);
	}

	@Override
	public SearchUniqueResult<E> findById(PK id) throws SearchException {
		return findById(id, null);
	}
	

	@Override
	public SearchResult<E> findByRestrictions(
			List<Restriction> restrictions, int first, int max,
			Ordering... orderings) throws SearchException {
		return findByRestrictions(restrictions, first, max, null, orderings);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public SearchResult<E> findByRestrictions(List<Restriction> restrictions, boolean useOperatorOr, int first, int max,
			String entityGraphName, Ordering... orderings){
		long start = System.currentTimeMillis();
		CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> criteriaQuery = cBuilder.createQuery(getEntityClass());
		criteriaQuery.distinct(true);
		Root<E> from = criteriaQuery.from(getEntityClass());
		Map<String, Path<?>> mapFieldPaths = new HashMap<>();
		addDefaultRestrictionFieldStatus(restrictions);
		List<Predicate> predicates = createQueryRestrictionsPredicates(restrictions, cBuilder, criteriaQuery, from, mapFieldPaths);
		if (!predicates.isEmpty()) {
			Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
			if (useOperatorOr) {
				criteriaQuery.where(cBuilder.or(predicatesArray));
			} else {
				criteriaQuery.where(predicatesArray);
			}
		}
		if (orderings != null && orderings.length > 0) {
			List<javax.persistence.criteria.Order> orders = new ArrayList<>();
			for (Ordering entry : orderings) {
				if (entry != null) {
					String field = entry.getField();
					Path<?> path = null;
					if (!mapFieldPaths.isEmpty()&& mapFieldPaths.containsKey(field)) {
						path = mapFieldPaths.get(field);
					}
					if (path == null) {
						String[] fieldPathArray = field.split("[.]");
						if (fieldPathArray != null && fieldPathArray.length > 1) {
							for (int i = 0; i < fieldPathArray.length - 1; i++) {
								String fieldPath = fieldPathArray[i];
								if (i == 0) {
									path = from.get(fieldPath);
								} else {
									path = path.get(fieldPath);
								}
							}
							path = path
									.get(fieldPathArray[fieldPathArray.length - 1]);
						} else {
							path = from.get(field);
						}
					}
					if (path != null) {
						if (entry.isAscending()) {
							orders.add(cBuilder.asc(path));
						} else {
							orders.add(cBuilder.desc(path));
						}
					}
				}
			}
			if (!orders.isEmpty()) {
				criteriaQuery.orderBy(orders);
			}
		}
		TypedQuery<E> query = getEntityManager().createQuery(criteriaQuery);
		if (entityGraphName != null) {
			try{
				EntityGraph graph = getEntityManager().getEntityGraph(entityGraphName);
				query.setHint(FETCH_GRAPH, graph);
			} catch (IllegalArgumentException e) {
				logger.warn(String.format(FETCH_GRAPH_MSG, entityGraphName), e);
			}
		}
		if (first > -1 && max > 0) {
			query.setFirstResult(first);
			query.setMaxResults(max);
		}
		List<E> resultList = query.getResultList();
		return getSearchUtil().searchResult(getEntityClass(), resultList, System.currentTimeMillis() - start);
	}

	/* (non-Javadoc)
	 * @see br.com.framework.model.dao.api.BaseDao#findByRestrictions(java.util.List, int, int, java.lang.String, br.com.framework.search.impl.Ordering[])
	 */
	public SearchResult<E> findByRestrictions(
			List<Restriction> restrictions, int first, int max, String entityGraphName,
			Ordering... orderings){
		return this.findByRestrictions(restrictions, false, first, max, entityGraphName, orderings);
	}
	
	/**
	 * Adiciona uma restrição padrão para o campo status das entidades na hierarquia de {@link BaseEntityAudited}
	 * 
	 * @param restrictions
	 */
	protected void addDefaultRestrictionFieldStatus(List<Restriction> restrictions) {
		if (BaseEntityAudited.class.isAssignableFrom(getEntityClass()) && restrictions != null) {
			boolean isStatusPresent = false;
			for (Restriction restriction : restrictions) {
				if (restriction.getField().equals(FIELD_STATUS)) {
					isStatusPresent = true;
					break;
				}
			}
			if (!isStatusPresent) {
				restrictions.add(searchUtil.restriction(FIELD_STATUS, Operator.EQ, Status.ACTIVE));
			}
		}
	}

	@Override
	public SearchUniqueResult<Long> getCountFindByRestrictions(
			List<Restriction> restrictions) throws SearchException {
		return this.getCountFindByRestrictions(restrictions, false);
	}
	

	@Override
	public SearchResult<E> findByExample(E e, boolean isLike, boolean isCaseSensitive, int first,
			int max, String entityGraphName, Ordering... orderings){
		List<Restriction> restrictions = createFiltersMapByExample(e, isLike, isCaseSensitive);
		return findByRestrictions(restrictions, first, max, entityGraphName, orderings);
	}

	@Override
	public SearchResult<E> findByExample(E e, boolean isLike,
			boolean isCaseSensitive, int first, int max,
			Ordering... orderings){
		return findByExample(e, isLike, isCaseSensitive, first, max, null, orderings);
	}

	@Override
	public SearchUniqueResult<Long> getCountFindByExample(E e,
			boolean isLike, boolean isCaseSensitive){
		List<Restriction> restrictions = createFiltersMapByExample(e, isLike, isCaseSensitive);
		return getCountFindByRestrictions(restrictions);
	}
	

	@Override
	public SearchUniqueResult<E> findUniqueByExample(E e, boolean isLike, boolean isCaseSensitive,
			String entityGraphName){
		SearchResult<E> searchResult = findByExample(e, isLike, isCaseSensitive, -1, -1, entityGraphName);
		if (!searchResult.getResults().isEmpty()) {
			if (searchResult.getResults().size() > 1) {
				throw new NonUniqueResultException();
			} else {
				return getSearchUtil().searchUniqueResult(getEntityClass(), searchResult.getResults().get(0), searchResult.getExecutionTime());
			}
		} else {
			return getSearchUtil().searchUniqueResult(getEntityClass(), null, searchResult.getExecutionTime());
		}
	}

	@Override
	public SearchUniqueResult<E> findUniqueByExample(E e,
			boolean isLike, boolean isCaseSensitive){
		return findUniqueByExample(e, isLike, isCaseSensitive, null);
	}
	

	@Override
	public SearchUniqueResult<E> findUniqueByExample(E e, String entityGraphName){
		return findUniqueByExample(e, false, true, entityGraphName);
	}

	@Override
	public SearchUniqueResult<E> findUniqueByExample(E e){
		return findUniqueByExample(e, null);
	}
	
	/* (non-Javadoc)
	 * @see br.com.framework.model.dao.api.BaseDao#findUniqueByRestrictions(java.util.List, java.lang.String)
	 */
	@Override
	public SearchUniqueResult<E> findUniqueByRestrictions(
			List<Restriction> restrictions, String entityGraphName){
		return findUniqueByRestrictions(restrictions, false, entityGraphName);
	}
	
	/* (non-Javadoc)
	 * @see br.com.framework.model.dao.api.BaseDao#findUniqueByRestrictions(java.util.List, boolean, java.lang.String)
	 */
	@Override
	public SearchUniqueResult<E> findUniqueByRestrictions(List<Restriction> restrictions, boolean useOperatorOr,
			String entityGraphName){
		SearchResult<E> searchResult = findByRestrictions(restrictions, useOperatorOr, -1, -1, entityGraphName);
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

	/**
	 * Verifica se o tipo é equivalente a uma Data
	 * 
	 * @param clazz
	 * @return
	 */
	private boolean isAssignableFromDateType(Class<?> clazz) {
		return isAssignableFromUtilDateType(clazz) || isAssignableFromSqlDateType(clazz) || isAssignableFromTemporal(clazz);
	}
	
	/**
	 * Verifica se o tipo é equivalente a um dos tipos: {@link java.sql.Date}, {@link java.sql.Timestamp} ou {@link java.sql.Time}
	 * 
	 * @param clazz
	 * @return
	 */
	private boolean isAssignableFromUtilDateType(Class<?> clazz) {
		return Date.class.isAssignableFrom(clazz);
	}
	
	/**
	 * Verifica se o tipo é equivalente a um dos tipos: {@link java.sql.Date}, {@link java.sql.Timestamp} ou {@link java.sql.Time}
	 * 
	 * @param clazz
	 * @return
	 */
	private boolean isAssignableFromSqlDateType(Class<?> clazz) {
		return java.sql.Date.class.isAssignableFrom(clazz) || java.sql.Timestamp.class.isAssignableFrom(clazz) || java.sql.Time.class.isAssignableFrom(clazz);
	}
	
	/**
	 * Verifica se o tipo é equivalente a um dos tipos: {@link Temporal}
	 * 
	 * @param clazz
	 * @return
	 */
	private boolean isAssignableFromTemporal(Class<?> clazz) {
		return Temporal.class.isAssignableFrom(clazz);
	}

	/**
	 * Cria os predicados da query com base na lista de {@link Restriction}
	 * 
	 * @param restrictions
	 * @param isLike
	 * @param isCaseSensitive
	 * @param cBuilder
	 * @param criteriaQuery
	 * @param from
	 * @param mapFieldPaths
	 * @return
	 */
	protected List<Predicate> createQueryRestrictionsPredicates(
			List<Restriction> restrictions, CriteriaBuilder cBuilder, CriteriaQuery<?> criteriaQuery,
			Root<E> from, Map<String, Path<?>> mapFieldPaths) {
		List<Predicate> predicates = new ArrayList<>();
		if (restrictions != null && !restrictions.isEmpty()) {
			for (Restriction entry : restrictions) {
				Predicate predicate = createFieldPredicate(entry.getField(), entry.getOperator(), entry.getValue(), mapFieldPaths, from, cBuilder);
				if (predicate != null) {
					predicates.add(predicate);
				}
			}
		}
		return predicates;
	}

	/**
	 * Recupera o valor {@link Enum} correspondente ao valor passado para o campo.
	 * 
	 * @param fieldValue
	 * @param enumConstants
	 * @param like
	 * @param ignoreCase
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Enum getEnumValue(Object fieldValue, Enum[] enumConstants, boolean like, boolean ignoreCase) {
		Enum enumValue = null;
		String value = "";
		if (Enum.class.isAssignableFrom(fieldValue.getClass())) {
			value = ((Enum)fieldValue).name();
		} else {
			value = fieldValue.toString();
		}
		for (Enum enum1 : enumConstants) {
			boolean match = false;
			if (like) {
				if (ignoreCase) {
					match = enum1.name().toUpperCase().indexOf(value.toUpperCase()) > -1;
				} else {
					match = enum1.name().indexOf(value) > -1;
				}
			} else {
				if (ignoreCase) {
					match = enum1.name().equalsIgnoreCase(value);
				} else {
					match = enum1.name().equals(value);
				}
			}
			if (match) {
				enumValue = enum1;
				break;
			}
		}
		return enumValue;
	}
	
	/**
	 * Recupera a classe de um atributo pelo nome, da classe informada.
	 *  
	 * @param parentClass
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 */
	protected Class<?> getFieldClass(Class<?> parentClass, String fieldName) throws NoSuchFieldException {
		Class<?> result = null;
		Field declaredField = ReflectionUtils.getField(fieldName, parentClass);
		if (Collection.class.isAssignableFrom(declaredField.getType())) {
			result = (Class<?>) ((ParameterizedType)declaredField.getGenericType()).getActualTypeArguments()[0];
		} else {
			result = declaredField.getType();
		}
		return result;
	}
	
	/**
	 * Recupera a classe de um atributo pelo nome, do {@link Path} informado.
	 * 
	 * @param path
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 */
	protected Class<?> getFieldClass(Path<?> path, String fieldName) throws NoSuchFieldException {
		Class<?> result = null;
		Class<?> pathJavaType = path.getJavaType();
		if (Collection.class.isAssignableFrom(pathJavaType)) {
			Field declaredField = ReflectionUtils.getField(fieldName, path.getParentPath().getJavaType());
			result = (Class<?>) ((ParameterizedType)declaredField.getGenericType()).getActualTypeArguments()[0];
		} else {
			result = pathJavaType;
		}
		return result;
	}
	
	/**
	 * Recupera o mÃ©todo get do field informado.
	 *  
	 * @param path
	 * @param fieldName
	 * @return
	 */
	protected Method getFieldGetter(Path<?> path, String fieldName) {
		return ReflectionUtils.getFieldGetter(path.getParentPath().getJavaType(), path.getJavaType(), fieldName);
	}

	/**
	 * Cria uma {@link List} de {@link Restriction} com base na entidade de exemplo.
	 * 
	 * @param entityExample
	 * @param isLike
	 * @param isCaseSensitive
	 * @return
	 */
	protected List<Restriction> createFiltersMapByExample(E entityExample, boolean isLike, boolean isCaseSensitive) {
		List<Restriction> restrictions = new ArrayList<>();
		if (entityExample != null) {
			buildFiltersMap(entityExample, restrictions, isLike, isCaseSensitive, "");
		}
		return restrictions;
	}

	/**
	 * MÃ©todo recursivo para construção do mapa de filtros.
	 * 
	 * @param entityExample
	 * @param restrictions
	 * @param isLike
	 * @param isCaseSensitive
	 * @param parent
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void buildFiltersMap(E entityExample, List<Restriction> restrictions, boolean isLike, boolean isCaseSensitive, String parent) {
		Field[] fields = ReflectionUtils.getAllFields(entityExample.getClass());
		if (fields != null && fields.length > 0) {
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (!Modifier.isStatic(field.getModifiers())) {
					Object value = null;
					try {
						if (!field.isAccessible()) {
							field.setAccessible(true);
						}
						value = field.get(entityExample);
					} catch (Exception e) {
						logger.error(String.format("Erro ao obter valor para EntityClass (%s) / Field (%s)", 
								getEntityClass().getName(), field.getName()), e);
					}
					if (value != null) {
						Class<? extends Class> classType = (Class) field.getType();
						String fieldPath = !parent.isEmpty() ? parent + "."
								+ field.getName() : field.getName();
						if (String.class.isAssignableFrom(classType)) {
							if (!((String) value).isEmpty()) {
								Operator operator = null;
								if (isLike) {
									if (isCaseSensitive) {
										operator = Operator.LK;
									} else {
										operator = Operator.LI;
									}
								} else {
									if (isCaseSensitive) {
										operator = Operator.EQ;
									} else {
										operator = Operator.EI;
									}
								}
								restrictions.add(getSearchUtil().restriction(fieldPath, operator, value));
							}
						} else {
							if (classType.isAnnotationPresent(Entity.class) 
									&& BaseEntity.class.isAssignableFrom(classType)) {
								E entityValue = (E) value;
								if (entityValue.getId() != null) {
									restrictions.add(getSearchUtil().restriction(fieldPath, Operator.EQ, value));
								} else {
									buildFiltersMap(entityValue, restrictions, isLike, isCaseSensitive, fieldPath);
								}
							} else {
								restrictions.add(getSearchUtil().restriction(fieldPath, Operator.EQ, value));
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void detach(E entity) {
		getEntityManager().detach(entity);
	}

	@Override
	public SearchResult<E> findByRestriction(Restriction restriction, int first, int max, String entityGraphName,
			Ordering... orderings) throws SearchException {
		return findByRestrictions(Arrays.asList(restriction), first, max, entityGraphName, orderings);
	}

	@Override
	public SearchResult<E> findByRestriction(Restriction restriction, String entityGraphName, Ordering... orderings)
			throws SearchException {
		return findByRestriction(restriction, -1, -1, entityGraphName, orderings);
	}

	@Override
	public SearchUniqueResult<E> findUniqueByRestrictions(List<Restriction> restrictions, boolean useOperatorOr){
		return findUniqueByRestrictions(restrictions, useOperatorOr, null);
	}

	@Override
	public SearchResult<E> findByRestrictions(List<Restriction> restrictions, boolean useOperatorOr,
			Ordering... orderings) throws SearchException {
		return findByRestrictions(restrictions, useOperatorOr, -1, -1, orderings);
	}

	@Override
	public SearchResult<E> findByRestrictions(List<Restriction> restrictions, boolean useOperatorOr, int first, int max,
			Ordering... orderings) throws SearchException {
		return findByRestrictions(restrictions, useOperatorOr, first, max, null, orderings);
	}

	@Override
	public SearchUniqueResult<Long> getCountFindByRestrictions(List<Restriction> restrictions, boolean useOperatorOr)
			throws SearchException {
		long start = System.currentTimeMillis();
		CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = cBuilder.createQuery(Long.class);
		Root<E> from = criteriaQuery.from(getEntityClass());
		criteriaQuery.select(cBuilder.count(from));
		addDefaultRestrictionFieldStatus(restrictions);
		List<Predicate> predicates = createQueryRestrictionsPredicates(restrictions, cBuilder, criteriaQuery, from, new HashMap<String, Path<?>>());
		if (!predicates.isEmpty()) {
			Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
			if (useOperatorOr) {
				criteriaQuery.where(cBuilder.or(predicatesArray));
			} else {
				criteriaQuery.where(predicatesArray);
			}
		}
		TypedQuery<Long> query = getEntityManager().createQuery(criteriaQuery);
		Long result = query.getSingleResult();
		return getSearchUtil().searchUniqueResult(Long.class, result, System.currentTimeMillis() - start);
	}

	@Override
	public SearchUniqueResult<Long> getCountInserteds(LocalDateTime referentialDateTime) throws SearchException {
		List<Restriction> restrictions = new ArrayList<>();
		if (BaseEntityAudited.class.isAssignableFrom(getDocumentClass())) {
			if (referentialDateTime != null) {
				restrictions.add(SearchUtil.instance().restriction("createDate", Operator.GE, referentialDateTime));
			}
			restrictions.add(SearchUtil.instance().restriction(FIELD_STATUS, Operator.EQ, Status.ACTIVE));
		}
		return this.getCountFindByRestrictions(restrictions);
	}

	@Override
	public SearchResult<E> getInserteds(LocalDateTime referentialDateTime, int first, int max, Ordering... orderings)
			throws SearchException {
		List<Restriction> restrictions = new ArrayList<>();
		if (BaseEntityAudited.class.isAssignableFrom(getDocumentClass())) {
			if (referentialDateTime != null) {
				restrictions.add(SearchUtil.instance().restriction("createDate", Operator.GE, referentialDateTime));
			}
			restrictions.add(SearchUtil.instance().restriction(FIELD_STATUS, Operator.EQ, Status.ACTIVE));
		}
		return this.findByRestrictions(restrictions, first, max, orderings);
	}

	@Override
	public SearchUniqueResult<Long> getCountUpdateds(LocalDateTime referentialDateTime) throws SearchException {
		List<Restriction> restrictions = new ArrayList<>();
		if (BaseEntityAudited.class.isAssignableFrom(getDocumentClass())) {
			if (referentialDateTime != null) {
				restrictions.add(SearchUtil.instance().restriction(FIELD_LAST_MODIFIED_DATE, Operator.GE, referentialDateTime));
			}
			restrictions.add(SearchUtil.instance().restriction(FIELD_STATUS, Operator.EQ, Status.ACTIVE));
		}
		return this.getCountFindByRestrictions(restrictions);
	}

	@Override
	public SearchResult<E> getUpdateds(LocalDateTime referentialDateTime, int first, int max, Ordering... orderings)
			throws SearchException {
		List<Restriction> restrictions = new ArrayList<>();
		if (BaseEntityAudited.class.isAssignableFrom(getEntityClass())) {
			if (referentialDateTime != null) {
				restrictions.add(SearchUtil.instance().restriction(FIELD_LAST_MODIFIED_DATE, Operator.GE, referentialDateTime));
			}
			restrictions.add(SearchUtil.instance().restriction(FIELD_STATUS, Operator.EQ, Status.ACTIVE));
		}
		return this.findByRestrictions(restrictions, first, max, orderings);
	}

	@Override
	public SearchUniqueResult<Long> getCountDeleteds(LocalDateTime referentialDateTime) throws SearchException {
		List<Restriction> restrictions = new ArrayList<>();
		if (BaseEntityAudited.class.isAssignableFrom(getEntityClass())) {
			if (referentialDateTime != null) {
				restrictions.add(SearchUtil.instance().restriction(FIELD_LAST_MODIFIED_DATE, Operator.GE, referentialDateTime));
			}
			restrictions.add(SearchUtil.instance().restriction(FIELD_STATUS, Operator.EQ, Status.INACTIVE));
		}
		return this.getCountFindByRestrictions(restrictions);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SearchResult<PK> getDeleteds(LocalDateTime referentialDateTime, int first, int max, Ordering... orderings)
			throws SearchException {
		long start = System.currentTimeMillis();
		List<Restriction> restrictions = new ArrayList<>();
		if (BaseEntityAudited.class.isAssignableFrom(getEntityClass())) {
			if (referentialDateTime != null) {
				restrictions.add(SearchUtil.instance().restriction(FIELD_LAST_MODIFIED_DATE, Operator.GE, referentialDateTime));
			}
			restrictions.add(SearchUtil.instance().restriction(FIELD_STATUS, Operator.EQ, Status.INACTIVE));
		}
		SearchResult<E> findByRestrictions = this.findByRestrictions(restrictions, first, max, orderings);
		List<PK> deletedIds = new ArrayList<>();
		Class<PK> classPK = null;
		for (E entity : findByRestrictions.getResults()) {
			if (classPK == null) {
				classPK = (Class<PK>) entity.getId().getClass();
			}
			deletedIds.add(entity.getId());
		}
		return searchUtil.searchResult(classPK, deletedIds, System.currentTimeMillis() - start);
	}
	
	/**
	 * Retorna o {@link Path} do campo a ser ordendado.
	 * 
	 * @param field
	 * @param from
	 * @param mapFieldPaths
	 * @return
	 */
	protected Path<?> getFieldPathToOrder(String field, Root<E> from, Map<String, Path<?>> mapFieldPaths) {
		Path<?> path = null;
		if (!mapFieldPaths.isEmpty() && mapFieldPaths.containsKey(field)) {
			path = mapFieldPaths.get(field);
		}
		if (path == null) {
			String[] fieldPathArray = field.split("[.]");
			if (fieldPathArray != null && fieldPathArray.length > 1) {
				for (int i = 0; i < fieldPathArray.length - 1; i++) {
					String fieldPath = fieldPathArray[i];
					if (i == 0) {
						path = from.get(fieldPath);
					} else {
						path = path.get(fieldPath);
					}
				}
				path = path.get(fieldPathArray[fieldPathArray.length - 1]);
			} else {
				path = from.get(field);
			}
		}
		return path;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public PageResponse<E> findPage(PageRequest pageRequest){
		CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> criteriaQuery = cBuilder.createQuery(getEntityClass());
		criteriaQuery.distinct(true);
		Root<E> from = criteriaQuery.from(getEntityClass());
		from.alias(getEntityClass().getSimpleName().toLowerCase() + "Alias");
		// adiciona um predicado para registros ativos
		if (BaseEntityAudited.class.isAssignableFrom(getEntityClass()) && pageRequest.getFilters() != null) {
			boolean isStatusPresent = false;
			for (Entry<String, FilterMetadata> entry : pageRequest.getFilters().entrySet()) {
				if (entry.getKey().equals(FIELD_STATUS)) {
					isStatusPresent = true;
					break;
				}
			}
			if (!isStatusPresent) {
				pageRequest.getFilters().put(FIELD_STATUS, new FilterMetadata(Status.ACTIVE, Operator.EQ));
			}
		}
		
		Map<String, Path<?>> mapFieldPaths = new HashMap<>();
		setupQueryPredicates(pageRequest, cBuilder, criteriaQuery, from, mapFieldPaths);
		
		boolean hasSortField = (pageRequest.getSortField() != null && !pageRequest.getSortField().trim().isEmpty()) ;
		if (hasSortField || !pageRequest.getMultiSortMeta().isEmpty()) {
			List<javax.persistence.criteria.Order> orders = new ArrayList<>();
			if (hasSortField) {
				Path<?> sortFieldPath = getFieldPathToOrder(pageRequest.getSortField(), from, mapFieldPaths);
				if (pageRequest.getSortOrder().equals(Order.ASC)) {
					orders.add(cBuilder.asc(sortFieldPath));
				} else {
					orders.add(cBuilder.desc(sortFieldPath));
				}
			} else {
				for (SortMeta sortMeta: pageRequest.getMultiSortMeta()) {
					Path<?> sortFieldPath = getFieldPathToOrder(sortMeta.getField(), from, mapFieldPaths);
					if (sortMeta.getOrder().equals(Order.ASC)) {
						orders.add(cBuilder.asc(sortFieldPath));
					} else {
						orders.add(cBuilder.desc(sortFieldPath));
					}
				}
			}
			if (!orders.isEmpty()) {
				criteriaQuery.orderBy(orders);
			}
		}
		PageResponse<E> pageResponse = new PageResponse<>();
		
		// cria e executa a query de count
		CriteriaQuery<Long> criteriaCountQuery = cBuilder.createQuery(Long.class);
		Root<E> fromCount = criteriaCountQuery.from(getEntityClass());
		fromCount.alias(getEntityClass().getSimpleName().toLowerCase() + "Alias");
		criteriaCountQuery.select(cBuilder.count(fromCount));
		
		setupQueryPredicates(pageRequest, cBuilder, criteriaCountQuery, fromCount, null);
		
		TypedQuery<Long> countQuery = getEntityManager().createQuery(criteriaCountQuery);
		Long result = countQuery.getSingleResult();
		pageResponse.setTotalRegisters(result);
		
		if (result > 0) {
			TypedQuery<E> query = getEntityManager().createQuery(criteriaQuery);
			if (pageRequest.getEntityGraph() != null && !pageRequest.getEntityGraph().trim().isEmpty()) {
				try{
					EntityGraph graph = getEntityManager().getEntityGraph(pageRequest.getEntityGraph());
					query.setHint(FETCH_GRAPH, graph);
				} catch (IllegalArgumentException e) {
					logger.warn(String.format(FETCH_GRAPH_MSG, pageRequest.getEntityGraph()), e);
				}
			}
			if (pageRequest.getFirst() > -1 && pageRequest.getRows() > 0) {
				query.setFirstResult(pageRequest.getFirst());
				query.setMaxResults(pageRequest.getRows());
			}
			List<E> resultList = query.getResultList();
			pageResponse.setResults(resultList);
		}
		return pageResponse;
	}

	/**
	 * Configura os {@link Predicate} no objeto {@link Query}
	 * 
	 * @param pageRequest
	 * @param cBuilder
	 * @param criteriaQuery
	 * @param from
	 * @param mapFieldPaths
	 * @return
	 */
	protected void setupQueryPredicates(PageRequest pageRequest, CriteriaBuilder cBuilder, CriteriaQuery<?> criteriaQuery, 
			Root<E> from, Map<String, Path<?>> mapFieldPaths) {
		if (mapFieldPaths == null) {
			mapFieldPaths = new HashMap<>();
		}
		List<Predicate> predicates = createQueryPagePredicates(pageRequest, cBuilder, criteriaQuery, from, mapFieldPaths);
		if (StringUtils.isNoneEmpty(pageRequest.getGlobalFilter())) {
			addGlobalFilterPredicates(pageRequest, predicates, cBuilder, from, mapFieldPaths);
		}
		if (!predicates.isEmpty()) {
			Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
			if (pageRequest.isAndOperand()) {
				criteriaQuery.where(cBuilder.and(predicatesArray));
			} else {
				criteriaQuery.where(cBuilder.or(predicatesArray));
			}
		}
	}
	
	
	/**
	 * Cria os predicados de acordo com os filtros informados no {@link PageRequest}.
	 * 
	 * @param pageRequest
	 * @param cBuilder
	 * @param criteriaQuery
	 * @param from
	 * @param mapFieldPaths
	 * @return
	 */
	protected List<Predicate> createQueryPagePredicates(
			PageRequest pageRequest, CriteriaBuilder cBuilder, CriteriaQuery<?> criteriaQuery,
			Root<E> from, Map<String, Path<?>> mapFieldPaths) {
		List<Predicate> predicates = new ArrayList<>();
		if (pageRequest.getFilters() != null && !pageRequest.getFilters().isEmpty()) {
			for (Entry<String, FilterMetadata> entry : pageRequest.getFilters().entrySet()) {
				FilterMetadata filter = entry.getValue();
				Predicate predicate = createFieldPredicate(entry.getKey(), filter.getOperator(), filter.getValue(), mapFieldPaths, from, cBuilder);
				if (predicate != null) {
					predicates.add(predicate);
				}
			}
		}
		return predicates;
	}
	
	private <Y extends Comparable<? super Y>> Predicate returnPredicateByOperator(CriteriaBuilder cBuilder, Operator operator,Expression<? extends Y> x, Y y) {
		switch (operator) {
			case LT:
				return cBuilder.lessThan(x, y);
			case LE:
				return cBuilder.lessThanOrEqualTo(x, y);
			case GT:
				return cBuilder.greaterThan(x, y);
			case GE:
				return cBuilder.greaterThanOrEqualTo(x, y);
			default:
				return null;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Predicate returnDatePredicate(CriteriaBuilder cBuilder, Path<?> pathLeaf, Operator operator, Object value) {
		Predicate predicate = null;
		Date date = DateUtil.parseDate(value.toString());
		if (date != null) {
			if (isAssignableFromTemporal(pathLeaf.getJavaType())) {
				Instant instant = date.toInstant();
				LocalDateTime temporal = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
				if (!operator.equals(Operator.EQ)) {
					predicate = returnPredicateByOperator(cBuilder, operator, (Path) pathLeaf, temporal);
				} else {
					predicate = cBuilder.equal((Path) pathLeaf, temporal);
				}
			} else {
				if (!operator.equals(Operator.EQ)) {
					predicate = returnPredicateByOperator(cBuilder, operator, (Path) pathLeaf, date);
				} else {
					predicate = cBuilder.equal((Path) pathLeaf, date);
				}
			}
		}
		return predicate;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Predicate returnPredicateByOperatorFromPathAndNumber(CriteriaBuilder cBuilder, Operator operator, Path x, Number y) {
		switch (operator) {
		case LT:
			return cBuilder.lt(x, y);
		case LE:
			return cBuilder.le(x, y);
		case GT:
			return cBuilder.gt(x, y);
		case GE:
			return cBuilder.ge(x, y);
		default:
			return null;
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Predicate returnInNotInPredicate(Operator operator, Path path, Collection lista) {
		switch (operator) {
			case IN:
				return path.in(lista);
			case NI:
				return path.in(lista).not();
			default:
				return null;
		}
	}
	
	/**
	 * Adiciona o predicado criado na lista de predicados informada.
	 * 
	 * @param predicates
	 * @param fieldName
	 * @param operator
	 * @param filterValue
	 * @param mapFieldPaths
	 * @param from
	 * @param cBuilder
	 */
	protected void addGlobalFilterPredicate(List<Predicate> predicates, String fieldName, Operator operator, String filterValue, Map<String, Path<?>> mapFieldPaths, 
    		Root<E> from, CriteriaBuilder cBuilder) {
		Field fieldType = this.getFieldType(fieldName, mapFieldPaths, from);
    	Predicate predicate = this.createFieldPredicate(fieldName, operator, filterValue, mapFieldPaths, from, cBuilder);
    	if (predicate != null) {
    		predicates.add(predicate);
    	}
    }
	
	/**
	 * Retorna o {@link Field} 
	 * 
	 * @param fieldName
	 * @param mapFieldPaths
	 * @param from
	 * @return
	 */
	protected Field getFieldType(String fieldName, Map<String, Path<?>> mapFieldPaths, Root<E> from) {
		Path<?> pathLeaf = null;
		if (mapFieldPaths != null && mapFieldPaths.containsKey(fieldName)) {
			pathLeaf = mapFieldPaths.get(fieldName);
		}
		String leaf = fieldName;
		String[] fieldPathArray = fieldName.split("[.]");
		if (pathLeaf == null) {
			if (fieldPathArray != null && fieldPathArray.length > 1) {
				Join<Object, Object> leafJoin = null;
				for (int i = 0; i < fieldPathArray.length - 1; i++) {
					leaf = fieldPathArray[i];
					if (i == 0) {
						leafJoin = from.join(leaf);
					} else {
						leafJoin = leafJoin.join(leaf);
					}
				}
				leaf = fieldPathArray[fieldPathArray.length - 1];
				pathLeaf = leafJoin.get(leaf);
			} else {
				pathLeaf = from.get(fieldName);
			}
			mapFieldPaths.put(fieldName, pathLeaf);
		} else {
			leaf = fieldPathArray[fieldPathArray.length - 1];
		}
		return ReflectionUtils.getField(leaf, pathLeaf.getParentPath().getJavaType());
	}
	
	
	/**
	 * Cria um {@link Predicate} para o campo/operador/valor informados.
	 * 
	 * @param fieldName
	 * 	O field pode ser um atributo direto ou um atributo de um relacionamento. Ex: atributoDireto.atributoIndireto;
	 * @param operator
	 * @param value
	 * @param mapFieldPaths
	 * @param from
	 * @param cBuilder
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Predicate createFieldPredicate(String fieldName, Operator operator, Object value, Map<String, Path<?>> mapFieldPaths, Root<E> from, CriteriaBuilder cBuilder) {
		Path<?> pathLeaf = null;
		if (mapFieldPaths != null && mapFieldPaths.containsKey(fieldName)) {
			pathLeaf = mapFieldPaths.get(fieldName);
		}
		String leaf = fieldName;
		String[] fieldPathArray = fieldName.split("[.]");
		if (pathLeaf == null) {
			if (fieldPathArray != null && fieldPathArray.length > 1) {
				Join<Object, Object> leafJoin = null;
				for (int i = 0; i < fieldPathArray.length - 1; i++) {
					leaf = fieldPathArray[i];
					if (i == 0) {
						leafJoin = from.join(leaf, JoinType.LEFT);
					} else {
						leafJoin = leafJoin.join(leaf, JoinType.LEFT);
					}
				}
				leaf = fieldPathArray[fieldPathArray.length - 1];
				pathLeaf = leafJoin.get(leaf);
			} else {
				pathLeaf = from.get(fieldName);
			}
			mapFieldPaths.put(fieldName, pathLeaf);
		} else {
			leaf = fieldPathArray[fieldPathArray.length - 1];
		}
		Predicate predicate = null;
		Field field = ReflectionUtils.getField(leaf, pathLeaf.getParentPath().getJavaType());
		if (field == null) {
			logger.error(String.format("Field %s não encontrado na classe %s", leaf, pathLeaf.getParentPath().getJavaType().getSimpleName()));
			return null;
		}
		// valor nulo, não cria predicate
		if (value == null) {
			return null;
		}
		Class<?> fieldClass = field.getType();
		Method fieldGetter = getFieldGetter(pathLeaf, leaf); 
		boolean isIdField = fieldGetter.isAnnotationPresent(Id.class);
		fieldClass = (Class<?>) fieldGetter.getGenericReturnType();
		switch (operator) {
			case LT:
			case LE:
			case GT:
			case GE:
				if (isIdField) {
					if (Number.class.isAssignableFrom(value.getClass())) {
						predicate = returnPredicateByOperatorFromPathAndNumber(cBuilder, operator, (Path) pathLeaf, (Number) value);
					} else if (Map.class.isAssignableFrom(value.getClass())) {
						Map<String, Object> valuesMap = (Map<String, Object>) value;
						Object objectValue = valuesMap.get(leaf);
						if (Number.class.isAssignableFrom(objectValue.getClass())) {
							predicate = returnPredicateByOperatorFromPathAndNumber(cBuilder, operator, (Path) pathLeaf, (Number) objectValue);
						} else {
							predicate = returnPredicateByOperator(cBuilder, operator,(Path) pathLeaf, objectValue.toString());
						}
					} else {
						predicate = cBuilder.lessThan((Path) pathLeaf, value.toString());
					}
				} else {
					if (Number.class.isAssignableFrom(fieldClass)) {
						if (String.class.isAssignableFrom(value.getClass())) {
							if (NumberUtils.isNumber(value.toString().trim())) {
								Number number = NumberUtils.createNumber(value.toString().trim());
								predicate = returnPredicateByOperatorFromPathAndNumber(cBuilder, operator, (Path) pathLeaf, number);
							}
						} else {
							predicate = returnPredicateByOperatorFromPathAndNumber(cBuilder, operator, (Path) pathLeaf, (Number) value);
						}
					} else if (isAssignableFromDateType(fieldClass)) {
						predicate = returnDatePredicate(cBuilder, pathLeaf, operator, value);
					} else {
						predicate = returnPredicateByOperator(cBuilder, operator, (Path) pathLeaf, value.toString());
					}
				}
				break;
			case EQ:
				if (isIdField) {
					if (Number.class.isAssignableFrom(fieldClass)) {
						if (String.class.isAssignableFrom(value.getClass())) {
							if (NumberUtils.isNumber(value.toString().trim())) {
								Number number = NumberUtils.createNumber(value.toString().trim());
								predicate = cBuilder.equal((Path) pathLeaf, number);
							}
						} else if (Map.class.isAssignableFrom(value.getClass())) {
							Map<String, Object> valuesMap = (Map<String, Object>) value;
							Object objectValue = valuesMap.get(leaf);
							predicate = cBuilder.equal((Path) pathLeaf, objectValue);
						} else {
							predicate = cBuilder.equal((Path) pathLeaf, value);
						}
					} else if (String.class.isAssignableFrom(value.getClass())) {
						if (!((String) value).trim().isEmpty()) {
							predicate = cBuilder.equal((Path) pathLeaf, value);
						}
					} else if (Map.class.isAssignableFrom(value.getClass())) {
						Map<String, Object> valuesMap = (Map<String, Object>) value;
						Object objectValue = valuesMap.get(leaf);
						predicate = cBuilder.equal((Path) pathLeaf, objectValue);
					} else {
						predicate = cBuilder.equal((Path) pathLeaf, value);
					}
				} else {
					if (Enum.class.isAssignableFrom(fieldClass)) {
						Enum[] enumConstants = (Enum[])fieldClass.getEnumConstants();
						Enum enumValue = getEnumValue(value, enumConstants, false, false);
						predicate = cBuilder.equal((Path) pathLeaf, enumValue);
					} else if (Number.class.isAssignableFrom(fieldClass)) {
						if (String.class.isAssignableFrom(value.getClass())) {
							if (NumberUtils.isNumber(value.toString().trim())) {
								Number number = NumberUtils.createNumber(value.toString().trim());
								predicate = cBuilder.equal((Path) pathLeaf, number);
							}
						} else {
							predicate = cBuilder.equal((Path) pathLeaf, value);
						}
					}  else if (Boolean.class.isAssignableFrom(fieldClass)) {
						if (String.class.isAssignableFrom(value.getClass())) {
							Boolean booleanObject = BooleanUtils.toBooleanObject(value.toString());
							if (booleanObject != null) {
								predicate = returnPredicateByOperator(cBuilder, operator, (Path) pathLeaf, booleanObject);
							}
						} else {
							predicate = returnPredicateByOperator(cBuilder, operator, (Path) pathLeaf, (Boolean) value);
						}
					} else if (isAssignableFromDateType(fieldClass)) {
						predicate = returnDatePredicate(cBuilder, pathLeaf, operator, value);
					} else {
						predicate = cBuilder.equal((Path) pathLeaf, value);
					}
				}
				break;
			case EI:
				if (Enum.class.isAssignableFrom(fieldClass)) {
					Enum[] enumConstants = (Enum[])fieldClass.getEnumConstants();
					Enum enumValue = getEnumValue(value, enumConstants, false, true);
					predicate = cBuilder.equal((Path) pathLeaf, enumValue);
				} else if (Number.class.isAssignableFrom(pathLeaf.getJavaType())) {
					if (String.class.isAssignableFrom(value.getClass())) {
						if (NumberUtils.isNumber(value.toString().trim())) {
							Number number = NumberUtils.createNumber(value.toString().trim());
							predicate = cBuilder.equal((Path) pathLeaf, number);
						}
					} else {
						predicate = cBuilder.equal((Path) pathLeaf, value);
					}
				} else if (isAssignableFromDateType(pathLeaf.getJavaType())) {
					predicate = returnDatePredicate(cBuilder, pathLeaf, operator, value);
				} else {
					predicate = cBuilder.equal(cBuilder.upper((Path) pathLeaf), value.toString().toUpperCase());
				}
				break;
			case NE:
				if (isIdField) {
					if (String.class.isAssignableFrom(value.getClass())) {
						if (!((String) value).trim().isEmpty()) {
							predicate = cBuilder.notEqual((Path) pathLeaf, value);
						}
					} else if (Map.class.isAssignableFrom(value.getClass())) {
						Map<String, Object> valuesMap = (Map<String, Object>) value;
						Object objectValue = valuesMap.get(leaf);
						predicate = cBuilder.notEqual((Path) pathLeaf, objectValue);
					} else {
						predicate = cBuilder.notEqual((Path) pathLeaf, value);
					}
				} else {
					if (Enum.class.isAssignableFrom(fieldClass)) {
						Enum[] enumConstants = (Enum[])fieldClass.getEnumConstants();
						Enum enumValue = getEnumValue(value, enumConstants, false, false);
						predicate = cBuilder.notEqual((Path) pathLeaf, enumValue);
					} else if (Number.class.isAssignableFrom(pathLeaf.getJavaType())) {
						if (String.class.isAssignableFrom(value.getClass())) {
							if (NumberUtils.isNumber(value.toString().trim())) {
								Number number = NumberUtils.createNumber(value.toString().trim());
								predicate = cBuilder.notEqual((Path) pathLeaf, number);
							}
						} else {
							predicate = cBuilder.notEqual((Path) pathLeaf, value);
						}
					} else if (Boolean.class.isAssignableFrom(fieldClass)) {
						if (String.class.isAssignableFrom(value.getClass())) {
							Boolean booleanObject = BooleanUtils.toBooleanObject(value.toString());
							if (booleanObject != null) {
								predicate = returnPredicateByOperator(cBuilder, operator, (Path) pathLeaf, booleanObject);
							}
						} else {
							predicate = returnPredicateByOperator(cBuilder, operator, (Path) pathLeaf, (Boolean) value);
						}
					} else if (isAssignableFromDateType(pathLeaf.getJavaType())) {
						predicate = returnDatePredicate(cBuilder, pathLeaf, operator, value);
					} else {
						predicate = cBuilder.notEqual((Path) pathLeaf, value);
					}
				}
				break;
			case IN:
			case NI:
				try {
					if (isIdField) {
						if (Collection.class.isAssignableFrom(value.getClass())) {
							List<Object> itensIn = new ArrayList<>();
							Iterator<Serializable> iterator = ((Collection) value).iterator();
							while(iterator.hasNext()) {
								Object item = iterator.next();
								if (String.class.isAssignableFrom(item.getClass())) {
									if (!((String) item).trim().isEmpty()) {
										itensIn.add(item);
									}
								} else if (Map.class.isAssignableFrom(item.getClass())) {
									Map<String, Object> itemMap = (Map<String, Object>) item;
									Object itemValue = itemMap.get(leaf);
									itensIn.add(itemValue);
								} else {
									itensIn.add(item);
								}
							}
							if (!itensIn.isEmpty()) {
								predicate = returnInNotInPredicate(operator, pathLeaf, itensIn);
							}
						} else if (Map.class.isAssignableFrom(value.getClass())) {
							Map<String, Object> valuesMap = (Map<String, Object>) value;
							Object objectValue = valuesMap.get(leaf);
							predicate = returnInNotInPredicate(operator, pathLeaf, Arrays.asList(objectValue));						
						} else {
							predicate = returnInNotInPredicate(operator, pathLeaf, Arrays.asList(value));
						}
					} else {
						if (Enum.class.isAssignableFrom(fieldClass)) {
							List<Enum> itensIn = new ArrayList<>();
							Enum[] enumConstants = (Enum[])fieldClass.getEnumConstants();
							if (value instanceof Collection) {
								Iterator<Serializable> iterator = ((Collection) value).iterator();
								while(iterator.hasNext()) {
									Object item = iterator.next();
									Enum enumValue = getEnumValue(item, enumConstants, false, false);
									if (enumValue != null) {
										itensIn.add(enumValue);
									}
								}
							}
							if (!itensIn.isEmpty()) {
								predicate = returnInNotInPredicate(operator, pathLeaf, itensIn);
							}
						} else {
							if (Collection.class.isAssignableFrom(value.getClass())) {
								predicate = returnInNotInPredicate(operator, pathLeaf, (Collection) value);
							} else {
								predicate = returnInNotInPredicate(operator, pathLeaf, Arrays.asList(value));
							}
						}
					}
				} catch (Exception e) {
					logger.error(String.format("Erro ao criar predicado. Field %s, classe %s", leaf, pathLeaf.getParentPath().getJavaType().getSimpleName()), e);
				}
				break;
			case LK:
				if (Enum.class.isAssignableFrom(fieldClass)) {
					Enum[] enumConstants = (Enum[]) fieldClass.getEnumConstants();
					Enum enumValue = getEnumValue(value, enumConstants, true, false);
					if (enumValue != null) {
						predicate = cBuilder.equal((Path) pathLeaf, enumValue);
					}
				} else {
					predicate = cBuilder.like(
							CriteriaBuilderHelper.translate(cBuilder, (Path<String>) pathLeaf),
							CriteriaBuilderHelper.translate(cBuilder, "%" + value.toString() + "%"));
				}
				break;
			case LI:
				if (Enum.class.isAssignableFrom(fieldClass)) {
					Enum[] enumConstants = (Enum[]) fieldClass.getEnumConstants();
					Enum enumValue = getEnumValue(value, enumConstants, true, true);
					if (enumValue != null) {
						predicate = cBuilder.equal((Path) pathLeaf, enumValue);
					}
				} else {
					predicate = cBuilder.like(
							cBuilder.upper(CriteriaBuilderHelper.translate(cBuilder, (Path<String>) pathLeaf)),
							cBuilder.upper(CriteriaBuilderHelper.translate(cBuilder, "%" + value.toString().toUpperCase() + "%")));
				}
				break;
		}
		return predicate;
	}
	
	/**
	 * Adiciona na lista de {@link Predicate}, os predicados referente ao campo de filtro global.
	 * Deve ser sobrescrito na classe concreta para que sejam definidos os campos a serem comparados.
	 * 
	 * @param pageRequest
	 * @param cBuilder
	 * @param from
	 * @param mapFieldPaths
	 * @return
	 */
	protected void addGlobalFilterPredicates(PageRequest pageRequest, List<Predicate> predicates, CriteriaBuilder cBuilder, Root<E> from, Map<String, Path<?>> mapFieldPaths) {
	}
}
