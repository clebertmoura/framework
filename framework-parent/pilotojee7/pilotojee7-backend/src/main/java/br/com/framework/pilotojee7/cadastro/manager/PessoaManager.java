package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.PessoaDao;
import br.com.framework.pilotojee7.cadastro.domain.Pessoa;

/**
 * Manager da entidade Pessoa.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class PessoaManager extends AbstractPessoaManager<Pessoa, PessoaDao> {

	public PessoaManager() {
		super(Pessoa.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(PessoaDao searchable) {
		super.setSearch(searchable);
	}

}