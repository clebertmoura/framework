#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ${package}.cadastro.dao.PaisDao;
import ${package}.cadastro.domain.Pais;
import ${package}.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Pais.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class PaisManager extends AppBaseManagerImpl<Long, Pais, PaisDao> {

	public PaisManager() {
		super(Pais.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(PaisDao searchable) {
		super.setSearch(searchable);
	}

}