package br.com.framework.pilotojee7.cadastro.dao;

import javax.ejb.Stateless;

import br.com.framework.pilotojee7.cadastro.domain.Imagem;
import br.com.framework.pilotojee7.core.dao.AppBaseDaoImpl;

/**
 *  DAO da entidade Imagem.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class ImagemDao extends AppBaseDaoImpl<Long, Imagem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public ImagemDao() {
		super(Imagem.class);
	}
	
}
