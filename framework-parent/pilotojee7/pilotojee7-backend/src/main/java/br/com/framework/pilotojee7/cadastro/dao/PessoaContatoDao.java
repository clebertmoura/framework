package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.PessoaContato;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade PessoaContato.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class PessoaContatoDao extends AppBaseDaoImpl<Long, PessoaContato>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public PessoaContatoDao() {
		super(PessoaContato.class);
	}
	
}
