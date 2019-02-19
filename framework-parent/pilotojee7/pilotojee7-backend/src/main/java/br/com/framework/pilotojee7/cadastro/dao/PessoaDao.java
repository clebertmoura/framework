package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Pessoa;

/**
 *  DAO da entidade Pessoa.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class PessoaDao extends AbstractPessoaDao<Pessoa>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public PessoaDao() {
		super(Pessoa.class);
	}
	
}
