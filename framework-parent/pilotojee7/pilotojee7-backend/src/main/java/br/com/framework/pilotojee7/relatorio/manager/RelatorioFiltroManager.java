package br.com.framework.pilotojee7.relatorio.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;
import br.com.framework.pilotojee7.relatorio.dao.RelatorioFiltroDao;
import br.com.framework.pilotojee7.relatorio.domain.RelatorioFiltro;

/**
 * Manager da entidade RelatorioFiltro.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class RelatorioFiltroManager extends AppBaseManagerImpl<Long, RelatorioFiltro, RelatorioFiltroDao> {

	public RelatorioFiltroManager() {
		super(RelatorioFiltro.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(RelatorioFiltroDao searchable) {
		super.setSearch(searchable);
	}

}