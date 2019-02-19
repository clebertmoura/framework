package br.com.framework.pilotojee7.relatorio.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;
import br.com.framework.pilotojee7.relatorio.domain.Relatorio;

/**
 *  DAO da entidade Relatorio.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class RelatorioDao extends AppBaseDaoImpl<Long, Relatorio>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public RelatorioDao() {
		super(Relatorio.class);
	}
	
}
