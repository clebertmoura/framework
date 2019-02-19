package br.com.framework.pilotojee7.cadastro.dao;

import br.com.framework.pilotojee7.cadastro.domain.Pessoa;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO abstrato da entidade {@link Pessoa}
 */
public abstract class AbstractPessoaDao<P extends Pessoa> extends AppBaseDaoImpl<Long, P>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8718511813463655769L;

	public AbstractPessoaDao(Class<P> entityClass) {
		super(entityClass);
	}
	
}
