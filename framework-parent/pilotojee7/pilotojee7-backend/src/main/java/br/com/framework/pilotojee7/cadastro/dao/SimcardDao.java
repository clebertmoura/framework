package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Simcard;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Simcard.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class SimcardDao extends AppBaseDaoImpl<Long, Simcard>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public SimcardDao() {
		super(Simcard.class);
	}
	
}
