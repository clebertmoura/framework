/**
 * 
 */
package br.com.framework.pilotojee7.core.manager;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.model.manager.impl.BaseManagerImpl;
import br.com.framework.model.qualifiers.DataRepository;
import br.com.framework.search.api.Search;

/**
 * Implementação base para os componentes de negócio.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Tipo da chave primária
 * @param <E> Tipo da entidade
 * @param <B> Componente de search
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public abstract class AppBaseManagerImpl<PK extends Serializable, E extends BaseEntity<PK>, B extends Search<PK, E>>
		extends BaseManagerImpl<PK, E, B> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BUNDLE_MESSAGES = "Mensagens";
	
	/**
	 * @param entityClass
	 */
	public AppBaseManagerImpl(Class<E> entityClass) {
		super(entityClass);
	}
	
	@Override @Inject
	protected void setEntityManager(@DataRepository EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}

}
