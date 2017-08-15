package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.ModeloCelularDao;
import br.com.framework.pilotojee7.cadastro.domain.ModeloCelular;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade ModeloCelular.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class ModeloCelularManager extends AppBaseManagerImpl<Long, ModeloCelular, ModeloCelularDao> {

	public ModeloCelularManager() {
		super(ModeloCelular.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(ModeloCelularDao searchable) {
		super.setSearch(searchable);
	}

}