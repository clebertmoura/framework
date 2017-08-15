package br.com.framework.pilotojee7.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.ImagemDao;
import br.com.framework.pilotojee7.cadastro.domain.Imagem;
import br.com.framework.pilotojee7.core.manager.AppBaseManagerImpl;

/**
 * Manager da entidade Imagem.
 *
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
public class ImagemManager extends AppBaseManagerImpl<Long, Imagem, ImagemDao> {

	public ImagemManager() {
		super(Imagem.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	protected void setSearch(ImagemDao searchable) {
		super.setSearch(searchable);
	}

}