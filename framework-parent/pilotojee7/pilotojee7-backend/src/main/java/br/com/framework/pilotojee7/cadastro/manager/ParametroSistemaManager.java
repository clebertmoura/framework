package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.ParametroSistemaDao;
import br.com.framework.pilotojee7.cadastro.domain.ParametroSistema;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade ParametroSistema.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class ParametroSistemaManager extends AppBaseManagerImpl<Long, ParametroSistema, ParametroSistemaDao> {

	public ParametroSistemaManager() {
		super(ParametroSistema.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(ParametroSistemaDao searchable) {
		super.setSearch(searchable);
	}

}