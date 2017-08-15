package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Filial;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Filial.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class FilialDao extends AppBaseDaoImpl<Long, Filial>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public FilialDao() {
		super(Filial.class);
	}
	
}
