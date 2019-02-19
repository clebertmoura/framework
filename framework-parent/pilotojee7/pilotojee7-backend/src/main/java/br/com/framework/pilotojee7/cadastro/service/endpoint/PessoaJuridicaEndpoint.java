package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.PessoaJuridicaDao;
import br.com.framework.pilotojee7.cadastro.domain.PessoaJuridica;
import br.com.framework.pilotojee7.cadastro.manager.PessoaJuridicaManager;
import br.com.framework.pilotojee7.cadastro.service.resource.PessoaJuridicaResource;

/**
 * Endpoint de {@link PessoaJuridica}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/pessoaJuridica")
public class PessoaJuridicaEndpoint extends PessoaEndpoint<PessoaJuridica, PessoaJuridicaResource, PessoaJuridicaDao, PessoaJuridicaManager>{

	private static final long serialVersionUID = -1L;
	
	@Override
	@Inject
	protected void setManager(PessoaJuridicaManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(PessoaJuridicaDao search) {
		super.setSearch(search);
	}

	@Override
	public PessoaJuridica fromResource(PessoaJuridica entity, PessoaJuridicaResource resource) {
		if (entity == null) {
			entity = new PessoaJuridica();
		}
		entity = super.fromResource(entity, resource);
		// inicializa os atributos da entidade.
		entity.setCnpj(resource.getCnpj());
		entity.setNomeFantasia(resource.getNomeFantasia());
		// inicializa entidades relacionadas
		return entity;
	}

	@Override
	protected PessoaJuridicaResource toResource(PessoaJuridica entity) {
		return new PessoaJuridicaResource(entity);
	}
}