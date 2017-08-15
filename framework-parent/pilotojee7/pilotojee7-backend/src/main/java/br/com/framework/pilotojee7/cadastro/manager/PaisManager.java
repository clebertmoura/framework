package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.PaisDao;
import br.com.framework.pilotojee7.cadastro.domain.Pais;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Pais.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class PaisManager extends AppBaseManagerImpl<Long, Pais, PaisDao> {

	public PaisManager() {
		super(Pais.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(PaisDao searchable) {
		super.setSearch(searchable);
	}

}