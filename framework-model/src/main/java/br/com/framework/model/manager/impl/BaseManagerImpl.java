package br.com.framework.model.manager.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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

import org.keycloak.KeycloakPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.domain.api.BaseEntityAudited;
import br.com.framework.domain.enums.Status;
import br.com.framework.model.error.impl.ErrorBuilder;
import br.com.framework.model.error.impl.ErrorBusiness;
import br.com.framework.model.exception.ModelException;
import br.com.framework.model.locale.UtilLocale;
import br.com.framework.model.manager.api.BaseManager;
import br.com.framework.model.util.Constants;
import br.com.framework.search.api.Search;
import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.util.Config;
import br.com.framework.util.resource.MessageSource;

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
	
	protected MessageSource messageSource;
	
	@Resource
	protected transient SessionContext sessionContext;
	
	@Inject
	protected UtilLocale utilLocale;
	
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
	 * @return
	 */
	protected Config getConfig() {
		return config;
	}

	/**
	 * @param config
	 */
	protected void setConfig(Config config) {
		this.config = config;
	}

	/**
	 * @return
	 */
	protected MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * Deve ser injetado no bean concreto.
	 *  
	 * @param messageSource
	 */
	protected void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	/**
	 * @return
	 */
	protected Locale getLocale() {
		/*Locale locale = (Locale) this.sessionContext.getContextData().get(LocaleInterceptor.LOCALE);
		if (locale == null) {
			locale = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
		}*/
		return this.utilLocale.getLocale();
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
	
	/**
	 * Adiciona um {@link ErrorBusiness} na {@link List} de erros.
	 * 
	 * @param errors
	 * @param businessRuleCode
	 * @param key
	 * @param params
	 */
	protected void addError(List<ErrorBusiness> errors, Integer businessRuleCode, String key, Object... params) {
		errors.add(createErrorBusiness(businessRuleCode, key, params));
	}
	
	/**
	 * Adiciona um {@link ErrorBusiness} na {@link List} de erros.
	 * 
	 * @param errors
	 * @param key
	 * @param params
	 */
	protected void addError(List<ErrorBusiness> errors, String key, Object... params) {
		this.addError(errors, null, key, params);
	}

	/**
	 * Cria um {@link ErrorBusiness}
	 * 
	 * @param businessRuleCode
	 * @param key
	 * @param params
	 * @return
	 */
	protected ErrorBusiness createErrorBusiness(Integer businessRuleCode, String key, Object... params) {
		String message = getMessageSource().getMessage(getLocale(), key, params);
		return ErrorBuilder.buildErrorBusiness(businessRuleCode, message);
	}
	
	/**
	 * Cria um {@link ErrorBusiness}
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	protected ErrorBusiness createErrorBusiness(String key, Object... params) {
		return createErrorBusiness(null, key, params);
	}
	
	/**
     * Lança uma {@link ModelException} caso a {@link List} de erros contenha ao menos um {@link ErrorBusiness}
     * 
     * @param errors
     * @throws ModelException
     */
    protected void throwIfErros(List<ErrorBusiness> errors) throws ModelException{
    	if (errors != null && !errors.isEmpty()) {
            String message = getMessageSource().getMessage(getLocale(), "br.com.framework.model.business.violation", errors.size());
			throw new ModelException(message, errors);
        }
    }
    
    /**
     * Retorna uma lista de {@link ErrorBusiness} vazia.
     * 
     * @return
     * @throws ModelException
     */
    protected List<ErrorBusiness> newErrorList() throws ModelException{
    	return new ArrayList<ErrorBusiness>();
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
		setAuditedInfoOnInsert(entity);
		getEntityManager().persist(entity);
		return entity;
	}
	
	/**
	 * Seta as informações de auditoria ao inserir um registro.
	 * 
	 * @param entity
	 */
	@SuppressWarnings("rawtypes")
	protected void setAuditedInfoOnInsert(E entity) {
		if (BaseEntityAudited.class.isAssignableFrom(getEntityClass())) {
			// this will set the user id as userName
			String userName = null;
			try {
				userName = sessionContext.getCallerPrincipal().getName();
				if (sessionContext.getCallerPrincipal() instanceof KeycloakPrincipal) {
					KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) sessionContext.getCallerPrincipal();
					// this is how to get the real userName (or rather the login name)
					userName = keycloakPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername();
				}
			} catch (IllegalStateException e) { }
			((BaseEntityAudited<PK>) entity).setCreationAuthor(userName);
			((BaseEntityAudited<PK>) entity).setLastModificationAuthor(userName);

		}
	}
	
	/**
	 * Seta as informações de auditoria ao inserir um registro.
	 * 
	 * @param entity
	 */
	@SuppressWarnings("rawtypes")
	protected void setAuditedInfoOnUpdate(E entity) {
		if (BaseEntityAudited.class.isAssignableFrom(getEntityClass())) {
			// this will set the user id as userName
			String userName = null;
			try {
				userName = sessionContext.getCallerPrincipal().getName();
				if (sessionContext.getCallerPrincipal() instanceof KeycloakPrincipal) {
					KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) sessionContext.getCallerPrincipal();
					// this is how to get the real userName (or rather the login name)
					userName = keycloakPrincipal.getKeycloakSecurityContext().getIdToken().getPreferredUsername();
				}
			} catch (IllegalStateException e) { }
			BaseEntityAudited<PK> entityAudited = (BaseEntityAudited<PK>) entity;
			entityAudited.setLastModificationAuthor(userName);
		}
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
			setAuditedInfoOnUpdate(entity);
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
				setAuditedInfoOnUpdate(entity);
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