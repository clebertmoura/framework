package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.FilialDao;
import br.com.framework.pilotojee7.cadastro.dao.FuncionarioDao;
import br.com.framework.pilotojee7.cadastro.dao.PessoaFisicaDao;
import br.com.framework.pilotojee7.cadastro.dao.TipoFuncionarioDao;
import br.com.framework.pilotojee7.cadastro.domain.Filial;
import br.com.framework.pilotojee7.cadastro.domain.Funcionario;
import br.com.framework.pilotojee7.cadastro.domain.PessoaFisica;
import br.com.framework.pilotojee7.cadastro.domain.TipoFuncionario;
import br.com.framework.pilotojee7.cadastro.manager.FuncionarioManager;
import br.com.framework.pilotojee7.cadastro.service.resource.FuncionarioResource;
import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;

/**
 * Endpoint de {@link Funcionario}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/funcionario")
public class FuncionarioEndpoint extends BaseEntityAuditedResourceEndpointImpl<Long, Funcionario, FuncionarioResource, FuncionarioDao, FuncionarioManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private TipoFuncionarioDao tipoFuncionarioDao;
	@Inject
	private FilialDao filialDao;
	@Inject
	private PessoaFisicaDao pessoaFisicaDao;
	@Inject
	private PessoaFisicaEndpoint pessoaFisicaEndpoint;
	
	@Override
	@Inject
	protected void setManager(FuncionarioManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(FuncionarioDao search) {
		super.setSearch(search);
	}

	@Override
	public Funcionario fromResource(Funcionario entity, FuncionarioResource resource) {
		if (entity == null) {
			entity = new Funcionario();
		}
		// inicializa os atributos da entidade.
		entity.setMatricula(resource.getMatricula());
		// inicializa entidades relacionadas
		TipoFuncionario tipoFuncionario = loadEntityRelation(entity.getTipoFuncionario(), resource.getTipoFuncionario(), tipoFuncionarioDao);
		entity.setTipoFuncionario(tipoFuncionario);
		Filial filial = loadEntityRelation(entity.getFilial(), resource.getFilial(), filialDao);
		entity.setFilial(filial);
		if (resource.getPessoaFisica() != null) {
			PessoaFisica pessoaFisica = null;
			if (resource.getPessoaFisica().getId() != null) {
				pessoaFisica = pessoaFisicaDao.findById(resource.getPessoaFisica().getId()).getUniqueResult();
			}
			pessoaFisica = pessoaFisicaEndpoint.fromResource(pessoaFisica, resource.getPessoaFisica());
			entity.setPessoaFisica(pessoaFisica);
		}
		return entity;
	}

	@Override
	protected FuncionarioResource toResource(Funcionario entity) {
		return new FuncionarioResource(entity);
	}
}