package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.OperadoraDao;
import br.com.framework.pilotojee7.cadastro.domain.Operadora;
import br.com.framework.pilotojee7.cadastro.manager.OperadoraManager;
import br.com.framework.pilotojee7.cadastro.service.resource.OperadoraResource;
import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;


/**
 * Endpoint de {@link Operadora}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/operadora")
public class OperadoraEndpoint extends BaseEntityAuditedResourceEndpointImpl<Long, Operadora, OperadoraResource, OperadoraDao, OperadoraManager>{

	private static final long serialVersionUID = -1L;
	
	
	@Override
	@Inject
	protected void setManager(OperadoraManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(OperadoraDao search) {
		super.setSearch(search);
	}

	@Override
	public Operadora fromResource(Operadora entity, OperadoraResource resource) {
		if (entity == null) {
			entity = new Operadora();
		}
		// inicializa os atributos da entidade.
		entity.setNome(resource.getNome());
		// inicializa entidades relacionadas
		return entity;
	}

	@Override
	protected OperadoraResource toResource(Operadora entity) {
		return new OperadoraResource(entity);
	}
}