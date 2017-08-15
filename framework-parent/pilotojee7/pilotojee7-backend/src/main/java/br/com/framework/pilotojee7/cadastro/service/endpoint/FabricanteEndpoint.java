package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.FabricanteDao;
import br.com.framework.pilotojee7.cadastro.dao.PessoaJuridicaDao;
import br.com.framework.pilotojee7.cadastro.domain.Fabricante;
import br.com.framework.pilotojee7.cadastro.domain.PessoaJuridica;
import br.com.framework.pilotojee7.cadastro.manager.FabricanteManager;
import br.com.framework.pilotojee7.cadastro.service.resource.FabricanteResource;
import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;

/**
 * Endpoint de {@link Fabricante}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/fabricante")
public class FabricanteEndpoint extends BaseEntityAuditedResourceEndpointImpl<Long, Fabricante, FabricanteResource, FabricanteDao, FabricanteManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private PessoaJuridicaDao pessoaJuridicaDao;
	@Inject
	private PessoaJuridicaEndpoint pessoaJuridicaEndpoint;
	
	@Override
	@Inject
	protected void setManager(FabricanteManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(FabricanteDao search) {
		super.setSearch(search);
	}

	@Override
	public Fabricante fromResource(Fabricante entity, FabricanteResource resource) {
		if (entity == null) {
			entity = new Fabricante();
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
	protected FabricanteResource toResource(Fabricante entity) {
		return new FabricanteResource(entity);
	}
}