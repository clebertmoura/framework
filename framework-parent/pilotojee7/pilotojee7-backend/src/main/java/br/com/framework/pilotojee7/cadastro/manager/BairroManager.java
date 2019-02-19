package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.BairroDao;
import br.com.framework.pilotojee7.cadastro.domain.Bairro;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Bairro.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class BairroManager extends AppBaseManagerImpl<Long, Bairro, BairroDao> {

	public BairroManager() {
		super(Bairro.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(BairroDao searchable) {
		super.setSearch(searchable);
	}

}