package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.FuncionarioDao;
import br.com.framework.pilotojee7.cadastro.domain.Funcionario;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Funcionario.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class FuncionarioManager extends AppBaseManagerImpl<Long, Funcionario, FuncionarioDao> {

	public FuncionarioManager() {
		super(Funcionario.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(FuncionarioDao searchable) {
		super.setSearch(searchable);
	}

}