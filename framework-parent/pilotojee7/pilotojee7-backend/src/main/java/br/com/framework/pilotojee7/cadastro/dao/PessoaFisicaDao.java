package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.PessoaFisica;

/**
 *  DAO da entidade PessoaFisica.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class PessoaFisicaDao extends AbstractPessoaDao<PessoaFisica>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public PessoaFisicaDao() {
		super(PessoaFisica.class);
	}
	
}
