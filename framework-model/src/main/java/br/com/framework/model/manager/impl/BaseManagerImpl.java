package br.com.framework.model.manager.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.domain.api.BaseEntityAudited;
import br.com.framework.domain.enums.Status;
import br.com.framework.model.exception.ModelException;
import br.com.framework.model.manager.api.BaseManager;
import br.com.framework.model.util.Constantes;
import br.com.framework.search.api.Search;
import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.util.Config;

/**
 * Implementação base do {@link BaseManager} das entidades de domínio. 
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária
 * @param <E> Entidade de domínio.
 * @param <B> Interface de Pesquisa.
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public abstract class BaseManagerImpl<PK extends Serializable, E extends BaseEntity<PK>, B extends Search<PK, E>> implements BaseManager<PK, E, B> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected transient Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
    protected Validator validator;
	
	protected Class<E> entityClass;
	
	protected transient EntityManager entityManager;
	
	protected transient B search;
	
	protected Config config;
	
	@Resource
	protected transient SessionContext sessionContext;
	
	/**
	 * @param entityClass
	 */
	public BaseManagerImpl(Class<E> entityClass) {
		super();
		this.entityClass = entityClass;
		this.config = new Config(entityClass.getClassLoader(), Constantes.CONFIG_FILENAME);
	}
	
	/**
	 * Retorna a referência ao {@link EntityManager}.
	 * 
	 * @return
	 */
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	/**
	 * @param entityManager the entityManager to set
	 */
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Retorna o nome simples da classe da entidade.
	 * 
	 * @return
	 */
	public String getEntityClassSimpleName(){
		return getEntityClass().getSimpleName();
	}
	
	/**
	 * Retorna o nome completo da classe da entidade.
	 * 
	 * @return
	 */
	public String getEntityClassName(){
		return getEntityClass().getName();
	}

	/* (non-Javadoc)
	 * @see br.com.framework.model.manager.impl.BaseNegocio#insert(E)
	 */
	public E insert(E entity) throws ModelException {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Inserindo entidade %s.", getEntityClassSimpleName()));
		}
		checkBeanValidation(entity);
		validateEntityFields(entity, true);
		getEntityManager().persist(entity);
		return entity;
	}

	/**
	 * Valida a entidade antes de inserir ou atualizar
	 * @param entity
	 * @param isInsert
	 * @throws ConstraintViolationException
	 * @throws ModelException
	 */
	public void validateEntityFields(E entity, boolean isInsert) throws ModelException {}
	
	/**
	 * Este método deve ser sobrescrito caso haja necessidade de implementar validações antes de remover a entidade. 
	 * @param entity
	 * @throws ModelException
	 */
	public void validateRemove(E entity) throws ModelException {}

	/* (non-Javadoc)
	 * @see br.com.framework.model.manager.impl.BaseNegocio#update(E)
	 */
	public E update(E entity) throws ModelException {
		if (entity != null && entity.getId() != null) {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Alterando entidade %s, Id = %s.", 
						getEntityClassName(), entity.getId().toString()));
			}
			checkBeanValidation(entity);
			validateEntityFields(entity, false);
			entity = getEntityManager().merge(entity);
		} else {
			if (logger.isDebugEnabled())
				logger.debug(String.format("A entidade informada para atualização está nula. Class: %s", getEntityClassName()));
		}
		return entity;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.model.manager.impl.BaseNegocio#remove(E)
	 */
	public E remove(E entity) throws ModelException {
		if (entity != null && entity.getId() != null) {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Removendo entidade %s, Id = %s.", 
						getEntityClassSimpleName(), entity.getId().toString()));
			}
			if (!getEntityManager().contains(entity)) {
				entity = getEntityManager().merge(entity);
			}
			validateRemove(entity);
			if (BaseEntityAudited.class.isAssignableFrom(getEntityClass())) {
				((BaseEntityAudited<PK>)entity).setStatus(Status.INACTIVE);
				getEntityManager().merge(entity);
			} else {
				getEntityManager().remove(entity);
			}
		} else {
			if (logger.isDebugEnabled())
				logger.debug(String.format("A entidade informada para remoção está nula. Class: %s", getEntityClassName()));
		}
		return entity;
	}
	
	/* (non-Javadoc)
	 * @see br.com.framework.model.manager.api.BaseManager#remover(java.io.Serializable)
	 */
	@Override
	public E remove(PK id) throws ModelException {
		SearchUniqueResult<E> buscaPorId = getSearch().findById(id);
		if (buscaPorId.getUniqueResult() != null) {
			return remove(buscaPorId.getUniqueResult());
		} else {
			throw new EntityNotFoundException(String.format("A entidade %s com id %s, não foi encontrada.", getEntityClassSimpleName(), id));
		}
	}
	
	public E removeDefinitely(E entity) throws ModelException {
		if (entity != null && entity.getId() != null) {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Removendo definitivamente a entidade %s, Id = %s.", 
						getEntityClassSimpleName(), entity.getId().toString()));
			}
			if (!getEntityManager().contains(entity)) {
				entity = getEntityManager().merge(entity);
			}
			validateRemove(entity);
			getEntityManager().remove(entity);
		} else {
			if (logger.isDebugEnabled())
				logger.debug(String.format("A entidade informada para remoção está nula. Class: %s", getEntityClassName()));
		}
		return entity;
	}

	@Override
	public E removeDefinitely(PK id) throws ModelException {
		SearchUniqueResult<E> buscaPorId = getSearch().findById(id);
		if (buscaPorId.getUniqueResult() != null) {
			return removeDefinitely(buscaPorId.getUniqueResult());
		} else {
			throw new EntityNotFoundException(String.format("A entidade %s com id %s, não foi encontrada.", getEntityClassSimpleName(), id));
		}
	}

	/* (non-Javadoc)
	 * @see br.com.framework.model.manager.impl.BaseNegocio#refresh(E)
	 */
	public E refresh(E entity){
		if (entity != null && entity.getId() != null) {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Recarregando entidade %s, Id = %s.", 
						getEntityClassSimpleName(), entity.getId().toString()));
			}
			if (!getEntityManager().contains(entity)) {
				entity = getEntityManager().merge(entity);
			}
			getEntityManager().refresh(entity);
		} else {
			if (logger.isDebugEnabled())
				logger.debug(String.format("A entidade informada para recarregamento está nula. Class: %s", getEntityClassName()));
		}
		return entity;
	}
	
	/**
	 * Valida a entidade utilizando o Bean Validator
	 * @param entidade
	 * @throws ConstraintViolationException
	 */
	protected void checkBeanValidation(E entidade){
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<E>> violations = validator.validate(entidade);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }
	
	/* (non-Javadoc)
	 * @see br.com.framework.model.manager.impl.BaseNegocio#detach(E)
	 */
	public void detach(E entity){
		if (entity != null && entity.getId() != null) {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Desvinculando entidade %s, Id = %s.", 
						getEntityClassSimpleName(), entity.getId().toString()));
			}
			if (getEntityManager().contains(entity)) {
				getEntityManager().detach(entity);
			}
		} else {
			if (logger.isDebugEnabled())
				logger.warn(String.format("A entidade informada para desvinculação está nula. Class: %s", getEntityClassSimpleName()));
		}
	}
	
	/**
	 * @return the search
	 */
	protected B getSearch() {
		return search;
	}

	/**
	 * Este método deve ser sobrescrito na classe filha para indicar qual {@link Search} deve ser utilizada.
	 * 
	 * @param search the search to set
	 */
	protected void setSearch(B search) {
		this.search = search;
	}

	/**
	 * @return the entityClass
	 */
	protected Class<E> getEntityClass() {
		return entityClass;
	}

}