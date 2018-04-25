package br.com.framework.pilotojee7.relatorio.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;
import br.com.framework.pilotojee7.relatorio.domain.Categoria;

/**
 *  DAO da entidade Categoria.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class CategoriaDao extends AppBaseDaoImpl<Long, Categoria>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public CategoriaDao() {
		super(Categoria.class);
	}
	
}
