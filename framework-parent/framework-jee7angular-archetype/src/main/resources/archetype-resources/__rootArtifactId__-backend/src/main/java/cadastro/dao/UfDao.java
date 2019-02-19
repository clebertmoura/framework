#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.dao;

import javax.ejb.Stateless;

import ${package}.cadastro.domain.Uf;
import ${package}.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Uf.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class UfDao extends AppBaseDaoImpl<Long, Uf>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public UfDao() {
		super(Uf.class);
	}
	
}
