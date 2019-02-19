package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.ClienteDao;
import br.com.framework.pilotojee7.cadastro.dao.PessoaFisicaDao;
import br.com.framework.pilotojee7.cadastro.dao.PessoaJuridicaDao;
import br.com.framework.pilotojee7.cadastro.domain.Cliente;
import br.com.framework.pilotojee7.cadastro.domain.Pessoa;
import br.com.framework.pilotojee7.cadastro.domain.PessoaFisica;
import br.com.framework.pilotojee7.cadastro.domain.PessoaJuridica;
import br.com.framework.pilotojee7.cadastro.manager.ClienteManager;
import br.com.framework.pilotojee7.cadastro.service.resource.ClienteResource;
import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;

/**
 * Endpoint de {@link Cliente}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/cliente")
public class ClienteEndpoint extends BaseEntityAuditedResourceEndpointImpl<Long, Cliente, ClienteResource, ClienteDao, ClienteManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private PessoaFisicaDao pessoaFisicaDao;
	@Inject
	private PessoaFisicaEndpoint pessoaFisicaEndpoint;
	@Inject
	private PessoaJuridicaDao pessoaJuridicaDao;
	@Inject
	private PessoaJuridicaEndpoint pessoaJuridicaEndpoint;
	
	@Override
	@Inject
	protected void setManager(ClienteManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(ClienteDao search) {
		super.setSearch(search);
	}

	@Override
	public Cliente fromResource(Cliente entity, ClienteResource resource) {
		if (entity == null) {
			entity = new Cliente();
		}
		// inicializa os atributos da entidade.
		// inicializa entidades relacionadas
		Pessoa pessoa = null;
		if (resource.getPessoaFisica() != null) {
			PessoaFisica pessoaFisica = null;
			if (resource.getPessoaFisica().getId() != null) {
				pessoaFisica = pessoaFisicaDao.findById(resource.getPessoaFisica().getId()).getUniqueResult();
			}
			pessoa = pessoaFisica = pessoaFisicaEndpoint.fromResource(pessoaFisica, resource.getPessoaFisica());
		}
		if (resource.getPessoaJuridica() != null) {
			PessoaJuridica pessoaJuridica = null;
			if (resource.getPessoaJuridica().getId() != null) {
				pessoaJuridica = pessoaJuridicaDao.findById(resource.getPessoaJuridica().getId()).getUniqueResult();
			}
			pessoa = pessoaJuridica = pessoaJuridicaEndpoint.fromResource(pessoaJuridica, resource.getPessoaJuridica());
		}
		entity.setPessoa(pessoa);
		return entity;
	}

	@Override
	protected ClienteResource toResource(Cliente entity) {
		return new ClienteResource(entity);
	}
}