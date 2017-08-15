package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.TipoLogradouro;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade TipoLogradouro.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class TipoLogradouroDao extends AppBaseDaoImpl<Long, TipoLogradouro>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public TipoLogradouroDao() {
		super(TipoLogradouro.class);
	}
	
}
