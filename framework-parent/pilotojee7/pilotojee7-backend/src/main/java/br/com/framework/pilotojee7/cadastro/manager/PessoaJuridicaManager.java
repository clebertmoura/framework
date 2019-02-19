package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.PessoaJuridicaDao;
import br.com.framework.pilotojee7.cadastro.domain.PessoaJuridica;

/**
 * Manager da entidade PessoaJuridica.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class PessoaJuridicaManager extends AbstractPessoaManager<PessoaJuridica, PessoaJuridicaDao> {

	public PessoaJuridicaManager() {
		super(PessoaJuridica.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(PessoaJuridicaDao searchable) {
		super.setSearch(searchable);
	}

}