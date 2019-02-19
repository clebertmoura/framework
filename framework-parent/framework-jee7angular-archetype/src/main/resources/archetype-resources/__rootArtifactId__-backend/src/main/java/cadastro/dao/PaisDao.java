#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.dao;

import javax.ejb.Stateless;

import ${package}.cadastro.domain.Pais;
import ${package}.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Pais.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class PaisDao extends AppBaseDaoImpl<Long, Pais>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public PaisDao() {
		super(Pais.class);
	}
	
}
