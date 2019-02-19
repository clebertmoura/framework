package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Logradouro;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Logradouro.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class LogradouroDao extends AppBaseDaoImpl<Long, Logradouro>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public LogradouroDao() {
		super(Logradouro.class);
	}
	
}
