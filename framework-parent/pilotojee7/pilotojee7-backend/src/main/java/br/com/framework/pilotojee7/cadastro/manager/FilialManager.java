package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.FilialDao;
import br.com.framework.pilotojee7.cadastro.domain.Filial;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Filial.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class FilialManager extends AppBaseManagerImpl<Long, Filial, FilialDao> {

	public FilialManager() {
		super(Filial.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(FilialDao searchable) {
		super.setSearch(searchable);
	}

}