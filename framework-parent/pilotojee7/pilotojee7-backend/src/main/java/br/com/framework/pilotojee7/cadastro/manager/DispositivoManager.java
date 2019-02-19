package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.DispositivoDao;
import br.com.framework.pilotojee7.cadastro.domain.Dispositivo;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Dispositivo.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class DispositivoManager extends AppBaseManagerImpl<Long, Dispositivo, DispositivoDao> {
	
	public DispositivoManager() {
		super(Dispositivo.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(DispositivoDao searchable) {
		super.setSearch(searchable);
	}
	
}