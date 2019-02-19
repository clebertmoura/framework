package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.CelularDao;
import br.com.framework.pilotojee7.cadastro.domain.Celular;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Celular.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class CelularManager extends AppBaseManagerImpl<Long, Celular, CelularDao> {

	public CelularManager() {
		super(Celular.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(CelularDao searchable) {
		super.setSearch(searchable);
	}

}