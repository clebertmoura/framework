package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.PessoaContatoDao;
import br.com.framework.pilotojee7.cadastro.domain.PessoaContato;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade PessoaContato.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class PessoaContatoManager extends AppBaseManagerImpl<Long, PessoaContato, PessoaContatoDao> {

	public PessoaContatoManager() {
		super(PessoaContato.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(PessoaContatoDao searchable) {
		super.setSearch(searchable);
	}

}