package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.ParametroSistema;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade ParametroSistema.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class ParametroSistemaDao extends AppBaseDaoImpl<Long, ParametroSistema>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public ParametroSistemaDao() {
		super(ParametroSistema.class);
	}
	
}
