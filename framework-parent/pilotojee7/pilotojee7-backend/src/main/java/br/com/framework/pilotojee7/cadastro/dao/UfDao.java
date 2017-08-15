package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Uf;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Uf.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class UfDao extends AppBaseDaoImpl<Long, Uf>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public UfDao() {
		super(Uf.class);
	}
	
}
