#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ${package}.cadastro.dao.CidadeDao;
import ${package}.cadastro.domain.Cidade;
import ${package}.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Cidade.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class CidadeManager extends AppBaseManagerImpl<Long, Cidade, CidadeDao> {

	public CidadeManager() {
		super(Cidade.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(CidadeDao searchable) {
		super.setSearch(searchable);
	}

}