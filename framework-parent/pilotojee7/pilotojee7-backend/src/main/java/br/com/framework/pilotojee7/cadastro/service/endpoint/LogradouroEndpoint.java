package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.BairroDao;
import br.com.framework.pilotojee7.cadastro.dao.LogradouroDao;
import br.com.framework.pilotojee7.cadastro.dao.TipoLogradouroDao;
import br.com.framework.pilotojee7.cadastro.domain.Bairro;
import br.com.framework.pilotojee7.cadastro.domain.Logradouro;
import br.com.framework.pilotojee7.cadastro.domain.TipoLogradouro;
import br.com.framework.pilotojee7.cadastro.manager.LogradouroManager;
import br.com.framework.pilotojee7.cadastro.service.resource.LogradouroResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;

/**
 * Endpoint de {@link Logradouro}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/logradouro")
public class LogradouroEndpoint extends BaseEntityResourceEndpointImpl<Long, Logradouro, LogradouroResource, LogradouroDao, LogradouroManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private BairroDao bairroDao;
	@Inject
	private TipoLogradouroDao tipoLogradouroDao;
	
	@Override
	@Inject
	protected void setManager(LogradouroManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(LogradouroDao search) {
		super.setSearch(search);
	}

	@Override
	public Logradouro fromResource(Logradouro entity, LogradouroResource resource) {
		if (entity == null) {
			entity = new Logradouro();
		}
		// inicializa os atributos da entidade.
		entity.setCep(resource.getCep());
		entity.setNome(resource.getNome());
		// inicializa entidades relacionadas
		Bairro bairro = loadEntityRelation(entity.getBairro(), resource.getBairro(), bairroDao);
		entity.setBairro(bairro);
		TipoLogradouro tipoLogradouro = loadEntityRelation(entity.getTipoLogradouro(), resource.getTipoLogradouro(), tipoLogradouroDao);
		entity.setTipoLogradouro(tipoLogradouro);
		return entity;
	}

	@Override
	protected LogradouroResource toResource(Logradouro entity) {
		return new LogradouroResource(entity);
	}
}