package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.FabricanteDao;
import br.com.framework.pilotojee7.cadastro.domain.Fabricante;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Fabricante.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class FabricanteManager extends AppBaseManagerImpl<Long, Fabricante, FabricanteDao> {

	public FabricanteManager() {
		super(Fabricante.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(FabricanteDao searchable) {
		super.setSearch(searchable);
	}

}