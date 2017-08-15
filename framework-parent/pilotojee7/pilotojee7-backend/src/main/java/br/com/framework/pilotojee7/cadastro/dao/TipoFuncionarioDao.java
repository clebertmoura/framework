package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.TipoFuncionario;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade TipoFuncionario.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class TipoFuncionarioDao extends AppBaseDaoImpl<Long, TipoFuncionario>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public TipoFuncionarioDao() {
		super(TipoFuncionario.class);
	}
	
}
