package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.TipoLogradouroDao;
import br.com.framework.pilotojee7.cadastro.domain.TipoLogradouro;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade TipoLogradouro.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class TipoLogradouroManager extends AppBaseManagerImpl<Long, TipoLogradouro, TipoLogradouroDao> {

	public TipoLogradouroManager() {
		super(TipoLogradouro.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(TipoLogradouroDao searchable) {
		super.setSearch(searchable);
	}

}