package br.com.framework.pilotojee7.relatorio.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;
import br.com.framework.pilotojee7.relatorio.dao.CategoriaDao;
import br.com.framework.pilotojee7.relatorio.domain.Categoria;

/**
 * Manager da entidade Categoria.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class CategoriaManager extends AppBaseManagerImpl<Long, Categoria, CategoriaDao> {

	public CategoriaManager() {
		super(Categoria.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(CategoriaDao searchable) {
		super.setSearch(searchable);
	}

}