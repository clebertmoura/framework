package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.ImagemDao;
import br.com.framework.pilotojee7.cadastro.dao.PaisDao;
import br.com.framework.pilotojee7.cadastro.domain.Imagem;
import br.com.framework.pilotojee7.cadastro.domain.Pais;
import br.com.framework.pilotojee7.cadastro.manager.PaisManager;
import br.com.framework.pilotojee7.cadastro.service.resource.PaisResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;

/**
 * Endpoint de {@link Pais}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/pais")
public class PaisEndpoint extends BaseEntityResourceEndpointImpl<Long, Pais, PaisResource, PaisDao, PaisManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private ImagemDao imagemDao;
	
	@Override
	@Inject
	protected void setManager(PaisManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(PaisDao search) {
		super.setSearch(search);
	}

	@Override
	public Pais fromResource(Pais entity, PaisResource resource) {
		if (entity == null) {
			entity = new Pais();
		}
		// inicializa os atributos da entidade.
		entity.setNome(resource.getNome());
		entity.setCodigoOnuAlpha(resource.getCodigoOnuAlpha());
		entity.setCodigoOnu(resource.getCodigoOnu());
		// inicializa entidades relacionadas
		Imagem imagem = loadEntityRelation(entity.getImagem(), resource.getImagem(), imagemDao);
		entity.setImagem(imagem);
		return entity;
	}

	@Override
	protected PaisResource toResource(Pais entity) {
		return new PaisResource(entity);
	}
}