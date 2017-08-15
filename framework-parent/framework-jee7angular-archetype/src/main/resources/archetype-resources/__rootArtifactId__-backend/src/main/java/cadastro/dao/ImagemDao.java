#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.dao;

import javax.ejb.Stateless;

import ${package}.cadastro.domain.Imagem;
import ${package}.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Imagem.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class ImagemDao extends AppBaseDaoImpl<Long, Imagem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public ImagemDao() {
		super(Imagem.class);
	}
	
}
