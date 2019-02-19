package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.PessoaFisicaDao;
import br.com.framework.pilotojee7.cadastro.domain.PessoaFisica;
import br.com.framework.pilotojee7.cadastro.manager.PessoaFisicaManager;
import br.com.framework.pilotojee7.cadastro.service.resource.PessoaFisicaResource;

/**
 * Endpoint de {@link PessoaFisica}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/pessoaFisica")
public class PessoaFisicaEndpoint extends PessoaEndpoint<PessoaFisica, PessoaFisicaResource, PessoaFisicaDao, PessoaFisicaManager>{

	private static final long serialVersionUID = -1L;
	
	@Override
	@Inject
	protected void setManager(PessoaFisicaManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(PessoaFisicaDao search) {
		super.setSearch(search);
	}

	@Override
	public PessoaFisica fromResource(PessoaFisica entity, PessoaFisicaResource resource) {
		if (entity == null) {
			entity = new PessoaFisica();
		}
		entity = super.fromResource(entity, resource);
		// inicializa os atributos da entidade.
		entity.setCpf(resource.getCpf());
		entity.setDataNascimento(resource.getDataNascimento());
		entity.setRg(resource.getRg());
		entity.setNomeMae(resource.getNomeMae());
		entity.setNomePai(resource.getNomePai());
		entity.setGenero(resource.getGenero());
		// inicializa entidades relacionadas
		return entity;
	}

	@Override
	protected PessoaFisicaResource toResource(PessoaFisica entity) {
		return new PessoaFisicaResource(entity);
	}
}