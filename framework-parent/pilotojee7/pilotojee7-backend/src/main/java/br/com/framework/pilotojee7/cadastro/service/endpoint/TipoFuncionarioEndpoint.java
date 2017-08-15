package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.EmpresaDao;
import br.com.framework.pilotojee7.cadastro.dao.TipoFuncionarioDao;
import br.com.framework.pilotojee7.cadastro.domain.Empresa;
import br.com.framework.pilotojee7.cadastro.domain.TipoFuncionario;
import br.com.framework.pilotojee7.cadastro.manager.TipoFuncionarioManager;
import br.com.framework.pilotojee7.cadastro.service.resource.TipoFuncionarioResource;
import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;

/**
 * Endpoint de {@link TipoFuncionario}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/tipoFuncionario")
public class TipoFuncionarioEndpoint extends BaseEntityAuditedResourceEndpointImpl<Long, TipoFuncionario, TipoFuncionarioResource, TipoFuncionarioDao, TipoFuncionarioManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private EmpresaDao empresaDao;
	
	@Override
	@Inject
	protected void setManager(TipoFuncionarioManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(TipoFuncionarioDao search) {
		super.setSearch(search);
	}

	@Override
	public TipoFuncionario fromResource(TipoFuncionario entity, TipoFuncionarioResource resource) {
		if (entity == null) {
			entity = new TipoFuncionario();
		}
		// inicializa os atributos da entidade.
		entity.setDescricao(resource.getDescricao());
		// inicializa entidades relacionadas
		Empresa empresa = loadEntityRelation(entity.getEmpresa(), resource.getEmpresa(), empresaDao);
		entity.setEmpresa(empresa);
		return entity;
	}

	@Override
	protected TipoFuncionarioResource toResource(TipoFuncionario entity) {
		return new TipoFuncionarioResource(entity);
	}
}