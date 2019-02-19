package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.EmpresaDao;
import br.com.framework.pilotojee7.cadastro.dao.OperadoraDao;
import br.com.framework.pilotojee7.cadastro.dao.SimcardDao;
import br.com.framework.pilotojee7.cadastro.domain.Empresa;
import br.com.framework.pilotojee7.cadastro.domain.Operadora;
import br.com.framework.pilotojee7.cadastro.domain.Simcard;
import br.com.framework.pilotojee7.cadastro.manager.SimcardManager;
import br.com.framework.pilotojee7.cadastro.service.resource.SimcardResource;
import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;

/**
 * Endpoint de {@link Simcard}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/simcard")
public class SimcardEndpoint extends BaseEntityAuditedResourceEndpointImpl<Long, Simcard, SimcardResource, SimcardDao, SimcardManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private OperadoraDao operadoraDao;
	@Inject
	private EmpresaDao empresaDao;
	
	@Override
	@Inject
	protected void setManager(SimcardManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(SimcardDao search) {
		super.setSearch(search);
	}

	@Override
	public Simcard fromResource(Simcard entity, SimcardResource resource) {
		if (entity == null) {
			entity = new Simcard();
		}
		// inicializa os atributos da entidade.
		entity.setDdd(resource.getDdd());
		entity.setNumero(resource.getNumero());
		entity.setImei(resource.getImei());
		entity.setDataCompra(resource.getDataCompra());
		entity.setDataNotaFiscal(resource.getDataNotaFiscal());
		entity.setNotaFiscal(resource.getNotaFiscal());
		entity.setDataCancelamento(resource.getDataCancelamento());
		entity.setHabilitado(resource.getHabilitado());
		entity.setUsoSimcard(resource.getUsoSimcard());
		// inicializa entidades relacionadas
		Operadora operadora = loadEntityRelation(entity.getOperadora(), resource.getOperadora(), operadoraDao);
		entity.setOperadora(operadora);
		Empresa empresa = loadEntityRelation(entity.getEmpresa(), resource.getEmpresa(), empresaDao);
		entity.setEmpresa(empresa);
		return entity;
	}

	@Override
	protected SimcardResource toResource(Simcard entity) {
		return new SimcardResource(entity);
	}
}