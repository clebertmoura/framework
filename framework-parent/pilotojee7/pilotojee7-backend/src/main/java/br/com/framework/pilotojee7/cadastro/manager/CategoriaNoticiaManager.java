package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.CategoriaNoticiaDao;
import br.com.framework.pilotojee7.cadastro.domain.CategoriaNoticia;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade CategoriaNoticia.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class CategoriaNoticiaManager extends AppBaseManagerImpl<Long, CategoriaNoticia, CategoriaNoticiaDao> {

	public CategoriaNoticiaManager() {
		super(CategoriaNoticia.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(CategoriaNoticiaDao searchable) {
		super.setSearch(searchable);
	}

}