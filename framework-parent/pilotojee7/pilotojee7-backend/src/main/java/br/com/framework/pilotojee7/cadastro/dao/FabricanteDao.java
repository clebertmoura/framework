package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Fabricante;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Fabricante.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class FabricanteDao extends AppBaseDaoImpl<Long, Fabricante>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public FabricanteDao() {
		super(Fabricante.class);
	}
	
}
