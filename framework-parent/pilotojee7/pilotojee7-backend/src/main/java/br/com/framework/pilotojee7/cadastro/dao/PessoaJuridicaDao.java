package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.PessoaJuridica;

/**
 *  DAO da entidade PessoaJuridica.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class PessoaJuridicaDao extends AbstractPessoaDao<PessoaJuridica>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public PessoaJuridicaDao() {
		super(PessoaJuridica.class);
	}
	
}
