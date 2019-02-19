package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.EmpresaDao;
import br.com.framework.pilotojee7.cadastro.domain.Empresa;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Empresa.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class EmpresaManager extends AppBaseManagerImpl<Long, Empresa, EmpresaDao> {

	public EmpresaManager() {
		super(Empresa.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(EmpresaDao searchable) {
		super.setSearch(searchable);
	}

}