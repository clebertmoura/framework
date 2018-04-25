package br.com.framework.pilotojee7.relatorio.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;
import br.com.framework.pilotojee7.relatorio.domain.Filtro;

/**
 *  DAO da entidade Filtro.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class FiltroDao extends AppBaseDaoImpl<Long, Filtro>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public FiltroDao() {
		super(Filtro.class);
	}
	
	/**
	 * @param queryStr
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> executeQuery(String queryStr) throws PersistenceException {
		Query query = getEntityManager().createNativeQuery(queryStr);
		return query.getResultList();
	}
	
}
