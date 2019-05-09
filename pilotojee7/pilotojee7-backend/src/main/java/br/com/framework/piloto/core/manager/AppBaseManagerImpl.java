/**
 * 
 */
package br.com.framework.piloto.core.manager;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.model.locale.LocaleInterceptor;
import br.com.framework.model.manager.impl.BaseManagerImpl;
import br.com.framework.search.api.Search;
import br.com.framework.util.resource.MessageSource;

/**
 * Implementação base para os componentes de negócio.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária
 * @param <E> Tipo da entidade
 * @param <B> Componente de search
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({LocaleInterceptor.class})
public abstract class AppBaseManagerImpl<PK extends Serializable, E extends BaseEntity<PK>, B extends Search<PK, E>>
		extends BaseManagerImpl<PK, E, B> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * @param entityClass
	 */
	public AppBaseManagerImpl(Class<E> entityClass) {
		super(entityClass);
	}
	
	@PersistenceContext(unitName = "primary")
	@Override
	protected void setEntityManager(EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}
	
	@Override
	@Inject
	protected void setMessageSource(MessageSource messageSource) {
		super.setMessageSource(messageSource);
	}

}
