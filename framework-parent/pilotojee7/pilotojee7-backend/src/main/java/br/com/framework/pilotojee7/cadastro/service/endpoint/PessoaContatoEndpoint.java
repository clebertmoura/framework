package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.PessoaContatoDao;
import br.com.framework.pilotojee7.cadastro.domain.PessoaContato;
import br.com.framework.pilotojee7.cadastro.manager.PessoaContatoManager;
import br.com.framework.pilotojee7.cadastro.service.resource.PessoaContatoResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;

/**
 * Endpoint de {@link PessoaContato}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/pessoaContato")
public class PessoaContatoEndpoint extends BaseEntityResourceEndpointImpl<Long, PessoaContato, PessoaContatoResource, PessoaContatoDao, PessoaContatoManager>{

	private static final long serialVersionUID = -1L;
	
	@Override
	@Inject
	protected void setManager(PessoaContatoManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(PessoaContatoDao search) {
		super.setSearch(search);
	}

	@Override
	public PessoaContato fromResource(PessoaContato entity, PessoaContatoResource resource) {
		if (entity == null) {
			entity = new PessoaContato();
		}
		// inicializa os atributos da entidade.
		entity.setEmail(resource.getEmail());
		entity.setDdd(resource.getDdd());
		entity.setNumero(resource.getNumero());
		// inicializa entidades relacionadas
		return entity;
	}

	@Override
	protected PessoaContatoResource toResource(PessoaContato entity) {
		return new PessoaContatoResource(entity);
	}
}