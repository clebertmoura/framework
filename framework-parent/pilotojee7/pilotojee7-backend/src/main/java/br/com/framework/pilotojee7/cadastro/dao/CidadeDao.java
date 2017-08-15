package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Cidade;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Cidade.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class CidadeDao extends AppBaseDaoImpl<Long, Cidade>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public CidadeDao() {
		super(Cidade.class);
	}
	
}
