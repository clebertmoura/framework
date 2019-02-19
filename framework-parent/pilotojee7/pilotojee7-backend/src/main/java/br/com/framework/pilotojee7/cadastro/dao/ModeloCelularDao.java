package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.ModeloCelular;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade ModeloCelular.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class ModeloCelularDao extends AppBaseDaoImpl<Long, ModeloCelular>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public ModeloCelularDao() {
		super(ModeloCelular.class);
	}
	
}
