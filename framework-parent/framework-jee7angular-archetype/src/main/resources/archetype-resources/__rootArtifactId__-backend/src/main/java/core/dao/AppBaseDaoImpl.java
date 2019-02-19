#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.core.dao;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.model.dao.impl.BaseDaoImpl;
import br.com.framework.model.qualifiers.DataRepository;

/**
 * Implementação base do BaseDao da aplicação. Todas as classes de Dao devem estender essa classe.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AppBaseDaoImpl<PK extends Serializable, E extends BaseEntity<PK>> extends BaseDaoImpl<PK, E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppBaseDaoImpl(Class<E> entityClass) {
		super(entityClass);
	}
	
	@Inject
	public void setEntityManager(@DataRepository EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}
}
