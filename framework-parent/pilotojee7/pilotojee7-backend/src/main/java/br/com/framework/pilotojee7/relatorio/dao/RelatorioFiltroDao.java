package br.com.framework.pilotojee7.relatorio.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;
import br.com.framework.pilotojee7.relatorio.domain.RelatorioFiltro;

/**
 *  DAO da entidade RelatorioFiltro.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class RelatorioFiltroDao extends AppBaseDaoImpl<Long, RelatorioFiltro>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public RelatorioFiltroDao() {
		super(RelatorioFiltro.class);
	}
	
}
