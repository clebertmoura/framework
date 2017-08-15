package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.SimcardDao;
import br.com.framework.pilotojee7.cadastro.domain.Simcard;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Simcard.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class SimcardManager extends AppBaseManagerImpl<Long, Simcard, SimcardDao> {

	public SimcardManager() {
		super(Simcard.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(SimcardDao searchable) {
		super.setSearch(searchable);
	}

}