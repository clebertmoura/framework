package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.LogradouroDao;
import br.com.framework.pilotojee7.cadastro.domain.Logradouro;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Logradouro.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class LogradouroManager extends AppBaseManagerImpl<Long, Logradouro, LogradouroDao> {

	public LogradouroManager() {
		super(Logradouro.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(LogradouroDao searchable) {
		super.setSearch(searchable);
	}

}