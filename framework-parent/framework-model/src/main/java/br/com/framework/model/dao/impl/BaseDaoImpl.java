/**
 * 
 */
package br.com.framework.model.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Entity;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.domain.api.BaseEntityAudited;
import br.com.framework.domain.enums.Status;
import br.com.framework.model.dao.api.BaseDao;
import br.com.framework.model.dao.util.CriteriaBuilderHelper;
import br.com.framework.search.api.SearchResult;
import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.search.exception.SearchException;
import br.com.framework.search.impl.Operator;
import br.com.framework.search.impl.Ordering;
import br.com.framework.search.impl.Restriction;
import br.com.framework.search.impl.SearchImpl;
import br.com.framework.search.util.SearchUtil;
import br.com.framework.util.reflection.ReflectionUtils;

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
	
	public SearchUniqueResult<E> findById(PK id, String entityGraphName) throws SearchException, PersistenceException {
		long start = System.currentTimeMillis();
		Map<String, Object> hints = new HashMap<>();
		if (entityGraphName != null) {
			try{
				EntityGraph graph = getEntityManager().getEntityGraph(entityGraphName);
				hints.put("javax.persistence.fetchgraph", graph);
			} catch (IllegalArgumentException e) {
				logger.warn(String.format("EntityGraph %s não encontrado! O resultado será carregado de forma padrão.", entityGraphName), e);
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
			String entityGraphName, Ordering... orderings) throws SearchException, PersistenceException {
		long start = System.currentTimeMillis();
		CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> criteriaQuery = cBuilder.createQuery(getEntityClass());
		criteriaQuery.distinct(true);
		Root<E> from = criteriaQuery.from(getEntityClass());
		Map<String, Path<?>> mapFieldPaths = new HashMap<String, Path<?>>();
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
			List<javax.persistence.criteria.Order> orders = new ArrayList<javax.persistence.criteria.Order>();
			for (Ordering entry : orderings) {
				if (entry != null) {
					String field = entry.getField();
					Path<?> path = null;
					if (!mapFieldPaths.isEmpty()) {
						if (mapFieldPaths.containsKey(field)) {
							path = mapFieldPaths.get(field);
						}
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
				query.setHint("javax.persistence.fetchgraph", graph);
			} catch (IllegalArgumentException e) {
				logger.warn(String.format("EntityGraph %s não encontrado! O resultado será carregado de forma padrão.", entityGraphName), e);
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
			Ordering... orderings) throws SearchException, PersistenceException {
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
				if (restriction.getField().equals("status")) {
					isStatusPresent = true;
					break;
				}
			}
			if (!isStatusPresent) {
				restrictions.add(searchUtil.restriction("status", Operator.EQ, Status.ACTIVE));
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
			int max, String entityGraphName, Ordering... orderings)
			throws SearchException, PersistenceException {
		List<Restriction> restrictions = createFiltersMapByExample(e, isLike, isCaseSensitive);
		return findByRestrictions(restrictions, first, max, entityGraphName, orderings);
	}

	@Override
	public SearchResult<E> findByExample(E e, boolean isLike,
			boolean isCaseSensitive, int first, int max,
			Ordering... orderings) throws SearchException, PersistenceException {
		return findByExample(e, isLike, isCaseSensitive, first, max, null, orderings);
	}

	@Override
	public SearchUniqueResult<Long> getCountFindByExample(E e,
			boolean isLike, boolean isCaseSensitive)
			throws SearchException, PersistenceException {
		List<Restriction> restrictions = createFiltersMapByExample(e, isLike, isCaseSensitive);
		return getCountFindByRestrictions(restrictions);
	}
	

	@Override
	public SearchUniqueResult<E> findUniqueByExample(E e, boolean isLike, boolean isCaseSensitive,
			String entityGraphName) throws SearchException, PersistenceException, NonUniqueResultException {
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
			boolean isLike, boolean isCaseSensitive)
			throws SearchException, PersistenceException, NonUniqueResultException {
		return findUniqueByExample(e, isLike, isCaseSensitive, null);
	}
	

	@Override
	public SearchUniqueResult<E> findUniqueByExample(E e, String entityGraphName)
			throws SearchException, PersistenceException, NonUniqueResultException {
		return findUniqueByExample(e, false, true, entityGraphName);
	}

	@Override
	public SearchUniqueResult<E> findUniqueByExample(E e)
			throws SearchException, PersistenceException, NonUniqueResultException {
		return findUniqueByExample(e, null);
	}
	
	/* (non-Javadoc)
	 * @see br.com.framework.model.dao.api.BaseDao#findUniqueByRestrictions(java.util.List, java.lang.String)
	 */
	@Override
	public SearchUniqueResult<E> findUniqueByRestrictions(
			List<Restriction> restrictions, String entityGraphName) throws SearchException,
			NonUniqueResultException {
		return findUniqueByRestrictions(restrictions, false, entityGraphName);
	}
	
	/* (non-Javadoc)
	 * @see br.com.framework.model.dao.api.BaseDao#findUniqueByRestrictions(java.util.List, boolean, java.lang.String)
	 */
	@Override
	public SearchUniqueResult<E> findUniqueByRestrictions(List<Restriction> restrictions, boolean useOperatorOr,
			String entityGraphName) throws SearchException, NonUniqueResultException {
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
	 * Verifica se o tipo é equivalente da tipos de Data. 
	 * 
	 * @param clazz
	 * @return
	 */
	private boolean isAssignableFromDateType(Class<?> clazz) {
		return clazz.isAssignableFrom(LocalDateTime.class) ||clazz.isAssignableFrom(LocalDate.class) || clazz.isAssignableFrom(LocalTime.class) || 
				clazz.isAssignableFrom(Date.class) || clazz.isAssignableFrom(java.sql.Date.class) || clazz.isAssignableFrom(java.sql.Timestamp.class) || clazz.isAssignableFrom(java.sql.Time.class);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<Predicate> createQueryRestrictionsPredicates(
			List<Restriction> restrictions, CriteriaBuilder cBuilder, CriteriaQuery<?> criteriaQuery,
			Root<E> from, Map<String, Path<?>> mapFieldPaths) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (restrictions != null && !restrictions.isEmpty()) {
			for (Restriction entry : restrictions) {
				String field = entry.getField();
				Object value = entry.getValue();
				Operator operator = entry.getOperator();
				Path<?> path = null;
				if (mapFieldPaths != null && mapFieldPaths.containsKey(field)) {
					path = mapFieldPaths.get(field);
				}
				String leaf = field;
				String[] fieldPathArray = field.split("[.]");
				if (path == null) {
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
						path = leafJoin.get(fieldPathArray[fieldPathArray.length - 1]);
					} else {
						path = from.get(field);
					}
					mapFieldPaths.put(field, path);
				} else {
					leaf = fieldPathArray[fieldPathArray.length - 1];
				}
				Predicate predicate = null;
				Class<?> fieldClass = null;
				try {
					fieldClass = getFieldClass(path, leaf);
				} catch (NoSuchFieldException e) {
					logger.error(String.format("Field %s não encontrado na classe %s", leaf, path.getParentPath().getJavaType().getSimpleName()), e);
				}
				if (fieldClass == null || value == null){
					continue;
				}
				switch (operator) {
					case LT:
						if (path.getJavaType().isAssignableFrom(Number.class)) {
							predicate = cBuilder.lt((Path) path, (Number) value);
						} else if (isAssignableFromDateType(path.getJavaType())) {
							Date date = parseDate(value.toString());
							if (date != null) {
								predicate = cBuilder.lessThan((Path) path, date);
							}
						} else {
							predicate = cBuilder.lessThan((Path) path, value.toString());
						}
						break;
					case LE:
						if (path.getJavaType().isAssignableFrom(Number.class)) {
							predicate = cBuilder.le((Path) path, (Number) value);
						} else if (isAssignableFromDateType(path.getJavaType())) {
							Date date = parseDate(value.toString());
							if (date != null) {
								predicate = cBuilder.lessThanOrEqualTo((Path) path, date);
							}
						} else {
							predicate = cBuilder.lessThanOrEqualTo((Path) path, value.toString());
						}
						break;
					case GT:
						if (path.getJavaType().isAssignableFrom(Number.class)) {
							predicate = cBuilder.gt((Path) path, (Number) value);
						} else if (isAssignableFromDateType(path.getJavaType())) {
							Date date = parseDate(value.toString());
							if (date != null) {
								predicate = cBuilder.greaterThan((Path) path, date);
							}
						} else {
							predicate = cBuilder.greaterThan((Path) path, value.toString());
						}
						break;
					case GE:
						if (path.getJavaType().isAssignableFrom(Number.class)) {
							predicate = cBuilder.ge((Path) path, (Number) value);
						} else if (isAssignableFromDateType(path.getJavaType())) {
							Date date = parseDate(value.toString());
							if (date != null) {
								predicate = cBuilder.greaterThanOrEqualTo((Path) path, date);
							}
						} else {
							predicate = cBuilder.greaterThanOrEqualTo((Path) path, value.toString());
						}
						break;
					case EQ:
						if (Enum.class.isAssignableFrom(fieldClass)) {
							Enum[] enumConstants = (Enum[])fieldClass.getEnumConstants();
							Enum enumValue = getEnumValue(value, enumConstants, false, false);
							predicate = cBuilder.equal((Path) path, enumValue);
						} else if (Number.class.isAssignableFrom(path.getJavaType())) {
							if (String.class.isAssignableFrom(value.getClass())) {
								if (!((String)value).trim().isEmpty()) {
									predicate = cBuilder.equal((Path) path, value);
								}
							} else {
								predicate = cBuilder.equal((Path) path, value);
							}
						} else if (isAssignableFromDateType(path.getJavaType())) {
							Date date = parseDate(value.toString());
							if (date != null) {
								predicate = cBuilder.equal((Path) path, date);
							}
						} else {
							predicate = cBuilder.equal((Path) path, value);
						}
						break;
					case EI:
						if (Enum.class.isAssignableFrom(fieldClass)) {
							Enum[] enumConstants = (Enum[])fieldClass.getEnumConstants();
							Enum enumValue = getEnumValue(value, enumConstants, false, true);
							predicate = cBuilder.equal((Path) path, enumValue);
						} else if (Number.class.isAssignableFrom(path.getJavaType())) {
							if (String.class.isAssignableFrom(value.getClass())) {
								if (!((String)value).trim().isEmpty()) {
									predicate = cBuilder.equal((Path) path, value);
								}
							} else {
								predicate = cBuilder.equal((Path) path, value);
							}
						} else if (isAssignableFromDateType(path.getJavaType())) {
							Date date = parseDate(value.toString());
							if (date != null) {
								predicate = cBuilder.equal((Path) path, date);
							}
						} else {
							predicate = cBuilder.equal(cBuilder.upper((Path) path), value.toString().toUpperCase());
						}
						break;
					case NE:
						if (Enum.class.isAssignableFrom(fieldClass)) {
							Enum[] enumConstants = (Enum[])fieldClass.getEnumConstants();
							Enum enumValue = getEnumValue(value, enumConstants, false, false);
							predicate = cBuilder.notEqual((Path) path, enumValue);
						} else if (Number.class.isAssignableFrom(path.getJavaType())) {
							if (String.class.isAssignableFrom(value.getClass())) {
								if (!((String)value).trim().isEmpty()) {
									predicate = cBuilder.notEqual((Path) path, value);
								}
							} else {
								predicate = cBuilder.notEqual((Path) path, value);
							}
						} else if (isAssignableFromDateType(path.getJavaType())) {
							Date date = parseDate(value.toString());
							if (date != null) {
								predicate = cBuilder.notEqual((Path) path, date);
							}
						} else {
							predicate = cBuilder.notEqual((Path) path, value);
						}
						break;
					case IN:
						try {
							if (Enum.class.isAssignableFrom(fieldClass)) {
								List<Enum> itensIn = new ArrayList<Enum>();
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
									predicate = path.in(itensIn);
								}
							} else if (BaseEntity.class.isAssignableFrom(fieldClass)) {
								List<Long> itensIn = new ArrayList<Long>();
								Iterator<Serializable> iterator = ((Collection) value).iterator();
								while(iterator.hasNext()) {
									Serializable itemId = iterator.next();
									itensIn.add(Long.parseLong(itemId.toString()));
								}
								predicate = from.join(leaf).get("id").in(itensIn);
							} else {
								predicate = path.in((Collection) value);
							}
						} catch (Exception e) {
							logger.error(String.format("Erro ao criar predicado. Field %s, classe %s", leaf, path.getParentPath().getJavaType().getSimpleName()), e);
						}
						break;
					case NI:
						try {
							if (Enum.class.isAssignableFrom(fieldClass)) {
								List<Enum> itensIn = new ArrayList<Enum>();
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
									predicate = path.in(itensIn).not();
								}
							} else if (BaseEntity.class.isAssignableFrom(fieldClass)) {
								List<Long> itensIn = new ArrayList<Long>();
								Iterator<Serializable> iterator = ((Collection) value).iterator();
								while(iterator.hasNext()) {
									Serializable itemId = iterator.next();
									itensIn.add(Long.parseLong(itemId.toString()));
								}
								predicate = from.join(leaf).get("id").in(itensIn).not();
							} else {
								predicate = path.in((Collection) value).not();
							}
						} catch (Exception e) {
							logger.error(String.format("Erro ao criar predicado. Field %s, classe %s", leaf, path.getParentPath().getJavaType().getSimpleName()), e);
						}
						break;
					case LK:
						if (Enum.class.isAssignableFrom(fieldClass)) {
							Enum[] enumConstants = (Enum[]) fieldClass.getEnumConstants();
							Enum enumValue = getEnumValue(value, enumConstants, true, false);
							if (enumValue != null) {
								predicate = cBuilder.equal((Path) path, enumValue);
							}
						} else {
							predicate = cBuilder.like(
									CriteriaBuilderHelper.translate(cBuilder, (Path<String>) path),
									CriteriaBuilderHelper.translate(cBuilder, "%" + value.toString() + "%"));
						}
						break;
					case LI:
						if (Enum.class.isAssignableFrom(fieldClass)) {
							Enum[] enumConstants = (Enum[]) fieldClass.getEnumConstants();
							Enum enumValue = getEnumValue(value, enumConstants, true, true);
							if (enumValue != null) {
								predicate = cBuilder.equal((Path) path, enumValue);
							}
						} else {
							predicate = cBuilder.like(
									cBuilder.upper(CriteriaBuilderHelper.translate(cBuilder, (Path<String>) path)),
									cBuilder.upper(CriteriaBuilderHelper.translate(cBuilder, "%" + value.toString().toUpperCase() + "%")));
						}
						break;
				}
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
					match = enum1.name().toUpperCase().equals(value.toUpperCase());
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
	 * Cria uma {@link List} de {@link Restriction} com base na entidade de exemplo.
	 * 
	 * @param entityExample
	 * @param isLike
	 * @param isCaseSensitive
	 * @return
	 */
	protected List<Restriction> createFiltersMapByExample(E entityExample, boolean isLike, boolean isCaseSensitive) {
		List<Restriction> restrictions = new ArrayList<Restriction>();
		if (entityExample != null) {
			buildFiltersMap(entityExample, restrictions, isLike, isCaseSensitive, "");
		}
		return restrictions;
	}

	/**
	 * Método recursivo para construção do mapa de filtros.
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
	public SearchUniqueResult<E> findUniqueByRestrictions(List<Restriction> restrictions, boolean useOperatorOr)
			throws SearchException, NonUniqueResultException {
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

}
