package br.com.framework.model.manager.impl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.PersistenceException;
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
import br.com.framework.model.manager.sync.EntitySyncRequest;
import br.com.framework.model.manager.sync.EntitySyncResponse;
import br.com.framework.model.manager.sync.SyncPageResponse;
import br.com.framework.model.util.Constants;
import br.com.framework.search.api.Search;
import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.search.impl.Operator;
import br.com.framework.search.impl.Ordering;
import br.com.framework.search.impl.Restriction;
import br.com.framework.search.util.SearchUtil;
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

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
    protected Validator validator;
	
	protected Class<E> entityClass;
	
	protected EntityManager entityManager;
	
	protected B search;
	
	protected Config config;
	
	@Resource
	protected SessionContext sessionContext;
	
	/**
	 * @param entityClass
	 */
	public BaseManagerImpl(Class<E> entityClass) {
		super();
		this.entityClass = entityClass;
		this.config = new Config(entityClass.getClassLoader(), Constants.CONFIG_FILENAME);
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
	public E insert(E entity) throws PersistenceException, ConstraintViolationException, ModelException {
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
	public void validateEntityFields(E entity, boolean isInsert) throws ConstraintViolationException, ModelException {}
	
	/**
	 * Este método deve ser sobrescrito caso haja necessidade de implementar validações antes de remover a entidade. 
	 * @param entity
	 * @throws ModelException
	 */
	public void validateRemove(E entity) throws ModelException {}

	/* (non-Javadoc)
	 * @see br.com.framework.model.manager.impl.BaseNegocio#update(E)
	 */
	public E update(E entity) throws PersistenceException, ConstraintViolationException, ModelException {
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
	public E remove(E entity) throws PersistenceException, ConstraintViolationException, ModelException {
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
	public E remove(PK id) throws PersistenceException, ConstraintViolationException, ModelException {
		SearchUniqueResult<E> buscaPorId = getSearch().findById(id);
		if (buscaPorId.getUniqueResult() != null) {
			return remove(buscaPorId.getUniqueResult());
		} else {
			throw new EntityNotFoundException(String.format("A entidade %s com id %s, não foi encontrada.", getEntityClassSimpleName(), id));
		}
	}
	
	public E removeDefinitely(E entity) throws PersistenceException, ConstraintViolationException, ModelException {
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
	public E removeDefinitely(PK id) throws PersistenceException, ConstraintViolationException, ModelException {
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
	public E refresh(E entity) throws PersistenceException {
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
	protected void checkBeanValidation(E entidade) throws ConstraintViolationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<E>> violations = validator.validate(entidade);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }
	
	/* (non-Javadoc)
	 * @see br.com.framework.model.manager.impl.BaseNegocio#detach(E)
	 */
	public void detach(E entity) throws PersistenceException {
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
	
	/**
	 * Método utilizado na sincronização responsável por consultar a lista de entidades inseridas.
	 * 
	 * @param request
	 * @return
	 */
	protected SyncPageResponse<E> getInserted(EntitySyncRequest request) {
		Ordering[] orderings = request.getOrderings().toArray(new Ordering[request.getOrderings().size()]);
		List<Restriction> restrictions = request.getRestrictions();
		
		if (BaseEntityAudited.class.isAssignableFrom(getEntityClass())) {
			if (request.getLastSync() != null) {
				restrictions.add(SearchUtil.instance().restriction("createDate", Operator.GE, request.getLastSync()));
			}
			restrictions.add(SearchUtil.instance().restriction("status", Operator.EQ, Status.ACTIVE));
		}
		
		Long count = getSearch().getCountFindByRestrictions(restrictions).getUniqueResult();
		List<E> registers = getSearch().findByRestrictions(restrictions, request.getFirst(), request.getMax(), orderings).getResults();
		
		return new SyncPageResponse<E>(count, registers);
	}
	
	/**
	 * Método utilizado na sincronização responsável por consultar a lista de entidades atualizadas.
	 * Este método pode ser especialidado na implementação concreta.
	 * 
	 * @param request
	 * @return
	 */
	protected SyncPageResponse<E> getUpdated(EntitySyncRequest request) {
		Ordering[] orderings = request.getOrderings().toArray(new Ordering[request.getOrderings().size()]);
		
		List<Restriction> restrictions = request.getRestrictions();
		
		if (BaseEntityAudited.class.isAssignableFrom(getEntityClass())) {
			if (request.getLastSync() != null) {
				restrictions.add(SearchUtil.instance().restriction("lastModifiedDate", Operator.GE, request.getLastSync()));
			}
			restrictions.add(SearchUtil.instance().restriction("status", Operator.EQ, Status.ACTIVE));
		}
		
		Long count = getSearch().getCountFindByRestrictions(restrictions).getUniqueResult();
		List<E> registers = getSearch().findByRestrictions(restrictions, request.getFirst(), request.getMax(), orderings).getResults();
		
		return new SyncPageResponse<E>(count, registers);
	}
	
	/**
	 * Método utilizado na sincronização responsável por consultar a lista de entidades deletadas.
	 * Este método pode ser especialidado na implementação concreta.
	 * 
	 * @param request
	 * @return
	 */
	protected SyncPageResponse<PK> getDeleted(EntitySyncRequest request) {
		Ordering[] orderings = request.getOrderings().toArray(new Ordering[request.getOrderings().size()]);
		
		List<Restriction> restrictions = request.getRestrictions();
		
		if (BaseEntityAudited.class.isAssignableFrom(getEntityClass())) {
			if (request.getLastSync() != null) {
				restrictions.add(SearchUtil.instance().restriction("lastModifiedDate", Operator.GE, request.getLastSync()));
			}
			restrictions.add(SearchUtil.instance().restriction("status", Operator.EQ, Status.INACTIVE));
		}
		
		Long count = getSearch().getCountFindByRestrictions(restrictions).getUniqueResult();
		List<E> registers = getSearch().findByRestrictions(restrictions, request.getFirst(), request.getMax(), orderings).getResults();
		
		List<PK> registersIds = new ArrayList<>();
		for (E e : registers) {
			registersIds.add(e.getId());
		}
		
		return new SyncPageResponse<PK>(count, registersIds);
	}
	
	/**
	 * Método utilizado na sincronização responsável por consultar a lista de entidades expiradas.
	 * Este método pode ser especialidado na implementação concreta.
	 * 
	 * @param request
	 * @return
	 */
	protected SyncPageResponse<PK> getExpired(EntitySyncRequest request) {
		Ordering[] orderings = request.getOrderings().toArray(new Ordering[request.getOrderings().size()]);
		
		List<Restriction> restrictions = request.getRestrictions();
		
		if (BaseEntityAudited.class.isAssignableFrom(getEntityClass())) {
			restrictions.add(SearchUtil.instance().restriction("status", Operator.EQ, Status.ACTIVE));
		}
		
		Long count = getSearch().getCountFindByRestrictions(restrictions).getUniqueResult();
		List<E> registers = getSearch().findByRestrictions(restrictions, request.getFirst(), request.getMax(), orderings).getResults();
		
		List<PK> registersIds = new ArrayList<>();
		for (E e : registers) {
			registersIds.add(e.getId());
		}
		
		return new SyncPageResponse<PK>(count, registersIds);
	}
	
	/**
	 * Método utilizado para sincronização de registros da entidade.
	 * 
	 * @param request
	 * @return
	 */
	public EntitySyncResponse<PK, E> synchronize(EntitySyncRequest request) {
		LocalDateTime now = LocalDateTime.now();
		EntitySyncResponse<PK, E> response = new EntitySyncResponse<>();
		// inserted
		response.setInserted(getInserted(request));
		// expiredIds
		response.setExpired(getExpired(request));
		if (BaseEntityAudited.class.isAssignableFrom(getEntityClass())) {
			// updated
			response.setUpdated(getUpdated(request));
			// removedIds
			response.setDeleted(getDeleted(request));
		}
		response.setLastSync(now);
		return response;
	}

}

