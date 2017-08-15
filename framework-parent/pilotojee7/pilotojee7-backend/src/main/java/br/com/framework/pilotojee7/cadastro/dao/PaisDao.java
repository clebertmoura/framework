package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Pais;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Pais.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class PaisDao extends AppBaseDaoImpl<Long, Pais>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public PaisDao() {
		super(Pais.class);
	}
	
}
