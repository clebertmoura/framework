package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.UfDao;
import br.com.framework.pilotojee7.cadastro.domain.Uf;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Uf.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class UfManager extends AppBaseManagerImpl<Long, Uf, UfDao> {

	public UfManager() {
		super(Uf.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(UfDao searchable) {
		super.setSearch(searchable);
	}

}