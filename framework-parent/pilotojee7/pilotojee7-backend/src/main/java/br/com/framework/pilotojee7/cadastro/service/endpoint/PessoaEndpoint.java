package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.inject.Inject;

import br.com.framework.pilotojee7.cadastro.dao.AbstractPessoaDao;
import br.com.framework.pilotojee7.cadastro.dao.PessoaContatoDao;
import br.com.framework.pilotojee7.cadastro.domain.Pessoa;
import br.com.framework.pilotojee7.cadastro.domain.PessoaContato;
import br.com.framework.pilotojee7.cadastro.manager.AbstractPessoaManager;
import br.com.framework.pilotojee7.cadastro.service.resource.PessoaContatoResource;
import br.com.framework.pilotojee7.cadastro.service.resource.PessoaResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;
import br.com.framework.service.util.LoadRelatedEntityResource;

/**
 * Endpoint abstrato de {@link Pessoa}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
public abstract class PessoaEndpoint<P extends Pessoa, R extends PessoaResource<P>, D extends AbstractPessoaDao<P>, M extends AbstractPessoaManager<P, D>> extends BaseEntityResourceEndpointImpl<Long, P, R, D, M>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private EnderecoEndpoint enderecoEndpoint;
	@Inject
	private PessoaContatoDao pessoaContatoDao;
	@Inject
	private PessoaContatoEndpoint pessoaContatoEndpoint;

	@Override
	public P fromResource(P entity, R resource) {
		// inicializa os atributos da entidade.
		entity.setNomePessoa(resource.getNomePessoa());
		// inicializa entidades relacionadas
		if (resource.getEndereco() != null) {
			entity.setEndereco(enderecoEndpoint.fromResource(entity.getEndereco(), resource.getEndereco()));
		}
		loadEntityRelations(entity.getContatos(), resource.getContatos(), pessoaContatoDao, new LoadRelatedEntityResource<Pessoa, PessoaContato, PessoaContatoResource>(entity) {
			@Override
			public PessoaContato loadRelatedEntityResource(PessoaContato relatedEntity,
					PessoaContatoResource relatedResource) {
				relatedEntity = pessoaContatoEndpoint.fromResource(relatedEntity, relatedResource);
				relatedEntity.setPessoa(getParentEntity());
				return relatedEntity;
			}
		});
		return entity;
	}

}