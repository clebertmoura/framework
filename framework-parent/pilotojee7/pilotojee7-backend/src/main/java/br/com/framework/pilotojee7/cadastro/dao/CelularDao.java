package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Celular;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Celular.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class CelularDao extends AppBaseDaoImpl<Long, Celular>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public CelularDao() {
		super(Celular.class);
	}
	
}
