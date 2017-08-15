package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Endereco;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Endereco.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class EnderecoDao extends AppBaseDaoImpl<Long, Endereco>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public EnderecoDao() {
		super(Endereco.class);
	}
	
}
