#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ${package}.cadastro.dao.BairroDao;
import ${package}.cadastro.domain.Bairro;
import ${package}.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Bairro.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class BairroManager extends AppBaseManagerImpl<Long, Bairro, BairroDao> {

	public BairroManager() {
		super(Bairro.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(BairroDao searchable) {
		super.setSearch(searchable);
	}

}