#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.manager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ${package}.cadastro.dao.ImagemDao;
import ${package}.cadastro.domain.Imagem;
import ${package}.core.manager.AppBaseManagerImpl;

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