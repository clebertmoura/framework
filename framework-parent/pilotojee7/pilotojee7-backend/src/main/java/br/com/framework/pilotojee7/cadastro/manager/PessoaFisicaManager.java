package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.PessoaFisicaDao;
import br.com.framework.pilotojee7.cadastro.domain.PessoaFisica;

/**
 * Manager da entidade PessoaFisica.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class PessoaFisicaManager extends AbstractPessoaManager<PessoaFisica, PessoaFisicaDao> {

	public PessoaFisicaManager() {
		super(PessoaFisica.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(PessoaFisicaDao searchable) {
		super.setSearch(searchable);
	}

}