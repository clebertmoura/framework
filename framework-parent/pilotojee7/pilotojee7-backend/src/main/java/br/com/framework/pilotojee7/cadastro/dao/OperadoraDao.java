package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Operadora;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Operadora.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class OperadoraDao extends AppBaseDaoImpl<Long, Operadora>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public OperadoraDao() {
		super(Operadora.class);
	}
	
}
