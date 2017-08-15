package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Empresa;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Empresa.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class EmpresaDao extends AppBaseDaoImpl<Long, Empresa>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public EmpresaDao() {
		super(Empresa.class);
	}
	
}
