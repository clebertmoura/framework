#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ${package}.cadastro.dao.UfDao;
import ${package}.cadastro.domain.Uf;
import ${package}.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Uf.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class UfManager extends AppBaseManagerImpl<Long, Uf, UfDao> {

	public UfManager() {
		super(Uf.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(UfDao searchable) {
		super.setSearch(searchable);
	}

}