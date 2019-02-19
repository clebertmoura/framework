package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.EmpresaDao;
import br.com.framework.pilotojee7.cadastro.dao.PessoaJuridicaDao;
import br.com.framework.pilotojee7.cadastro.domain.Empresa;
import br.com.framework.pilotojee7.cadastro.domain.PessoaJuridica;
import br.com.framework.pilotojee7.cadastro.manager.EmpresaManager;
import br.com.framework.pilotojee7.cadastro.service.resource.EmpresaResource;
import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;

/**
 * Endpoint de {@link Empresa}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/empresa")
public class EmpresaEndpoint extends BaseEntityAuditedResourceEndpointImpl<Long, Empresa, EmpresaResource, EmpresaDao, EmpresaManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private PessoaJuridicaDao pessoaJuridicaDao;
	@Inject
	private PessoaJuridicaEndpoint pessoaJuridicaEndpoint;
	
	@Override
	@Inject
	protected void setManager(EmpresaManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(EmpresaDao search) {
		super.setSearch(search);
	}

	@Override
	public Empresa fromResource(Empresa entity, EmpresaResource resource) {
		if (entity == null) {
			entity = new Empresa();
		}
		// inicializa os atributos da entidade.
		// inicializa entidades relacionadas
		if (resource.getPessoaJuridica() != null) {
			PessoaJuridica pessoaJuridica = null;
			if (resource.getPessoaJuridica().getId() != null) {
				pessoaJuridica = pessoaJuridicaDao.findById(resource.getPessoaJuridica().getId()).getUniqueResult();
			}
			pessoaJuridica = pessoaJuridicaEndpoint.fromResource(pessoaJuridica, resource.getPessoaJuridica());
			entity.setPessoaJuridica(pessoaJuridica);
		}
		return entity;
	}

	@Override
	protected EmpresaResource toResource(Empresa entity) {
		return new EmpresaResource(entity);
	}
}