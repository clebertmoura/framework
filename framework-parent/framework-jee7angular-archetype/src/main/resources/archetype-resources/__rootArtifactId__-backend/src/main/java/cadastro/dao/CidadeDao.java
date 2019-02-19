#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.dao;

import javax.ejb.Stateless;

import ${package}.cadastro.domain.Cidade;
import ${package}.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Cidade.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class CidadeDao extends AppBaseDaoImpl<Long, Cidade>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public CidadeDao() {
		super(Cidade.class);
	}
	
}
