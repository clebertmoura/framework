package br.com.framework.piloto.core.dao;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.model.dao.impl.BaseDaoImpl;

/**
 * Implementação base do BaseDao da aplicação. Todas as classes de Dao devem estender essa classe.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AppBaseDaoImpl<PK extends Serializable, E extends BaseEntity<PK>> extends BaseDaoImpl<PK, E> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppBaseDaoImpl(Class<E> entityClass) {
		super(entityClass);
	}
	
	@PersistenceContext(unitName = "primary")
	public void setEntityManager(EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}
}
