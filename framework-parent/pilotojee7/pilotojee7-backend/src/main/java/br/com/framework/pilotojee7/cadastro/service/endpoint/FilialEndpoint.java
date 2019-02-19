package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.EmpresaDao;
import br.com.framework.pilotojee7.cadastro.dao.FilialDao;
import br.com.framework.pilotojee7.cadastro.dao.PessoaJuridicaDao;
import br.com.framework.pilotojee7.cadastro.domain.Empresa;
import br.com.framework.pilotojee7.cadastro.domain.Filial;
import br.com.framework.pilotojee7.cadastro.domain.PessoaJuridica;
import br.com.framework.pilotojee7.cadastro.manager.FilialManager;
import br.com.framework.pilotojee7.cadastro.service.resource.FilialResource;
import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;

/**
 * Endpoint de {@link Filial}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/filial")
public class FilialEndpoint extends BaseEntityAuditedResourceEndpointImpl<Long, Filial, FilialResource, FilialDao, FilialManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private PessoaJuridicaDao pessoaJuridicaDao;
	@Inject
	private PessoaJuridicaEndpoint pessoaJuridicaEndpoint;
	@Inject
	private EmpresaDao empresaDao;
	
	@Override
	@Inject
	protected void setManager(FilialManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(FilialDao search) {
		super.setSearch(search);
	}

	@Override
	public Filial fromResource(Filial entity, FilialResource resource) {
		if (entity == null) {
			entity = new Filial();
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
		Empresa empresa = loadEntityRelation(entity.getEmpresa(), resource.getEmpresa(), empresaDao);
		entity.setEmpresa(empresa);
		return entity;
	}

	@Override
	protected FilialResource toResource(Filial entity) {
		return new FilialResource(entity);
	}
}