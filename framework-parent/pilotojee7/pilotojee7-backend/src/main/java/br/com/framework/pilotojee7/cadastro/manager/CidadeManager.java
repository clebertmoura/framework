package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.CidadeDao;
import br.com.framework.pilotojee7.cadastro.domain.Cidade;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

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