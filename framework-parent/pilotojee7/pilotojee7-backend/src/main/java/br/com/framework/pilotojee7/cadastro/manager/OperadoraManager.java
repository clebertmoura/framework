package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.OperadoraDao;
import br.com.framework.pilotojee7.cadastro.domain.Operadora;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Operadora.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class OperadoraManager extends AppBaseManagerImpl<Long, Operadora, OperadoraDao> {

	public OperadoraManager() {
		super(Operadora.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(OperadoraDao searchable) {
		super.setSearch(searchable);
	}

}