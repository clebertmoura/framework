package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Cliente;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Cliente.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class ClienteDao extends AppBaseDaoImpl<Long, Cliente>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public ClienteDao() {
		super(Cliente.class);
	}
	
}
