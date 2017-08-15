package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Funcionario;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Funcionario.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class FuncionarioDao extends AppBaseDaoImpl<Long, Funcionario>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public FuncionarioDao() {
		super(Funcionario.class);
	}
	
}
