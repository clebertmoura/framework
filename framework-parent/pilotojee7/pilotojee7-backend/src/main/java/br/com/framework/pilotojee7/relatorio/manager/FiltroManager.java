package br.com.framework.pilotojee7.relatorio.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;
import br.com.framework.pilotojee7.relatorio.dao.FiltroDao;
import br.com.framework.pilotojee7.relatorio.domain.Filtro;

/**
 * Manager da entidade Filtro.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class FiltroManager extends AppBaseManagerImpl<Long, Filtro, FiltroDao> {

	public FiltroManager() {
		super(Filtro.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(FiltroDao searchable) {
		super.setSearch(searchable);
	}

}