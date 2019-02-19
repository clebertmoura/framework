package br.com.framework.pilotojee7.cadastro.manager;

import br.com.framework.pilotojee7.cadastro.dao.AbstractPessoaDao;
import br.com.framework.pilotojee7.cadastro.domain.Pessoa;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager Abstrato de {@link Pessoa}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public abstract class AbstractPessoaManager<P extends Pessoa, Dao extends AbstractPessoaDao<P>> extends AppBaseManagerImpl<Long, P, Dao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AbstractPessoaManager(Class<P> entityClass) {
		super(entityClass);
	}

}
