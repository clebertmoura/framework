package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Bairro;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Bairro.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class BairroDao extends AppBaseDaoImpl<Long, Bairro>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public BairroDao() {
		super(Bairro.class);
	}
	
}
