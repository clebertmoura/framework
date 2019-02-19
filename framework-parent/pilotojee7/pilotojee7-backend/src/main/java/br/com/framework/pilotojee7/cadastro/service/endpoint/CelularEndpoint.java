package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.CelularDao;
import br.com.framework.pilotojee7.cadastro.dao.FilialDao;
import br.com.framework.pilotojee7.cadastro.dao.ModeloCelularDao;
import br.com.framework.pilotojee7.cadastro.dao.SimcardDao;
import br.com.framework.pilotojee7.cadastro.domain.Celular;
import br.com.framework.pilotojee7.cadastro.domain.Filial;
import br.com.framework.pilotojee7.cadastro.domain.ModeloCelular;
import br.com.framework.pilotojee7.cadastro.domain.Simcard;
import br.com.framework.pilotojee7.cadastro.manager.CelularManager;
import br.com.framework.pilotojee7.cadastro.service.resource.CelularResource;
import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;

/**
 * Endpoint de {@link Celular}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/celular")
public class CelularEndpoint extends BaseEntityAuditedResourceEndpointImpl<Long, Celular, CelularResource, CelularDao, CelularManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private FilialDao filialDao;
	@Inject
	private SimcardDao simcardDao;
	@Inject
	private ModeloCelularDao modeloCelularDao;
	
	@Override
	@Inject
	protected void setManager(CelularManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(CelularDao search) {
		super.setSearch(search);
	}

	@Override
	public Celular fromResource(Celular entity, CelularResource resource) {
		if (entity == null) {
			entity = new Celular();
		}
		// inicializa os atributos da entidade.
		entity.setTrackingAtivo(resource.getTrackingAtivo());
		entity.setImei(resource.getImei());
		entity.setSerial(resource.getSerial());
		entity.setVersao(resource.getVersao());
		// inicializa entidades relacionadas
		Filial filial = loadEntityRelation(entity.getFilial(), resource.getFilial(), filialDao);
		entity.setFilial(filial);
		Simcard simcard = loadEntityRelation(entity.getSimcard(), resource.getSimcard(), simcardDao);
		entity.setSimcard(simcard);
		ModeloCelular modeloCelular = loadEntityRelation(entity.getModelo(), resource.getModelo(), modeloCelularDao);
		entity.setModelo(modeloCelular);
		return entity;
	}

	@Override
	protected CelularResource toResource(Celular entity) {
		return new CelularResource(entity);
	}
}