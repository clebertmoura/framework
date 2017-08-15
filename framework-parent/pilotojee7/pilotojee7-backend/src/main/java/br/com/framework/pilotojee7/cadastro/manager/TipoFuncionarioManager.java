package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.TipoFuncionarioDao;
import br.com.framework.pilotojee7.cadastro.domain.TipoFuncionario;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade TipoFuncionario.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class TipoFuncionarioManager extends AppBaseManagerImpl<Long, TipoFuncionario, TipoFuncionarioDao> {

	public TipoFuncionarioManager() {
		super(TipoFuncionario.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(TipoFuncionarioDao searchable) {
		super.setSearch(searchable);
	}

}