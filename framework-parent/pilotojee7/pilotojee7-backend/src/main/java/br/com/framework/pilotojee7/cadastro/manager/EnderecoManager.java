package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.EnderecoDao;
import br.com.framework.pilotojee7.cadastro.domain.Endereco;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Endereco.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class EnderecoManager extends AppBaseManagerImpl<Long, Endereco, EnderecoDao> {

	public EnderecoManager() {
		super(Endereco.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(EnderecoDao searchable) {
		super.setSearch(searchable);
	}

}