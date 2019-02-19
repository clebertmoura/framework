package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.ClienteDao;
import br.com.framework.pilotojee7.cadastro.domain.Cliente;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Cliente.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class ClienteManager extends AppBaseManagerImpl<Long, Cliente, ClienteDao> {

	public ClienteManager() {
		super(Cliente.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(ClienteDao searchable) {
		super.setSearch(searchable);
	}

}