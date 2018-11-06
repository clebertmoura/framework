/**
 * 
 */
package br.jus.framework.piloto.core.manager;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.jus.framework.domain.api.BaseEntity;
import br.jus.framework.model.manager.impl.BaseManagerImpl;
import br.jus.framework.search.api.Search;

/**
 * Implementação base para os componentes de negócio.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 * @param <PK> Tipo da chave primária
 * @param <E> Tipo da entidade
 * @param <B> Componente de search
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
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
	
	@PersistenceContext(unitName = "primary")
	protected void setEntityManager(EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}

}
