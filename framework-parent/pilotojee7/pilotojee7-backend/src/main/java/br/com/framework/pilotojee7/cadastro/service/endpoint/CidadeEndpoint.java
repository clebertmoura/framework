package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.CidadeDao;
import br.com.framework.pilotojee7.cadastro.dao.UfDao;
import br.com.framework.pilotojee7.cadastro.domain.Cidade;
import br.com.framework.pilotojee7.cadastro.domain.Uf;
import br.com.framework.pilotojee7.cadastro.manager.CidadeManager;
import br.com.framework.pilotojee7.cadastro.service.resource.CidadeResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;

/**
 * Endpoint de {@link Cidade}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/cidade")
public class CidadeEndpoint extends BaseEntityResourceEndpointImpl<Long, Cidade, CidadeResource, CidadeDao, CidadeManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private UfDao ufDao;
	
	@Override
	@Inject
	protected void setManager(CidadeManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(CidadeDao search) {
		super.setSearch(search);
	}

	@Override
	public Cidade fromResource(Cidade entity, CidadeResource resource) {
		if (entity == null) {
			entity = new Cidade();
		}
		// inicializa os atributos da entidade.
		entity.setNome(resource.getNome());
		entity.setCodigoIbge(resource.getCodigoIbge());
		// inicializa entidades relacionadas
		Uf uf = loadEntityRelation(entity.getUf(), resource.getUf(), ufDao);
		entity.setUf(uf);
		return entity;
	}

	@Override
	protected CidadeResource toResource(Cidade entity) {
		return new CidadeResource(entity);
	}
}