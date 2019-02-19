package br.com.framework.pilotojee7.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.cadastro.dao.BairroDao;
import br.com.framework.pilotojee7.cadastro.dao.CidadeDao;
import br.com.framework.pilotojee7.cadastro.dao.EnderecoDao;
import br.com.framework.pilotojee7.cadastro.dao.PaisDao;
import br.com.framework.pilotojee7.cadastro.dao.UfDao;
import br.com.framework.pilotojee7.cadastro.domain.Bairro;
import br.com.framework.pilotojee7.cadastro.domain.Cidade;
import br.com.framework.pilotojee7.cadastro.domain.Endereco;
import br.com.framework.pilotojee7.cadastro.domain.Pais;
import br.com.framework.pilotojee7.cadastro.domain.Uf;
import br.com.framework.pilotojee7.cadastro.manager.EnderecoManager;
import br.com.framework.pilotojee7.cadastro.service.resource.EnderecoResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;

/**
 * Endpoint de {@link Endereco}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/endereco")
public class EnderecoEndpoint extends BaseEntityResourceEndpointImpl<Long, Endereco, EnderecoResource, EnderecoDao, EnderecoManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private CidadeDao cidadeDao;
	@Inject
	private BairroDao bairroDao;
	@Inject
	private UfDao ufDao;
	@Inject
	private PaisDao paisDao;
	
	@Override
	@Inject
	protected void setManager(EnderecoManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(EnderecoDao search) {
		super.setSearch(search);
	}

	@Override
	public Endereco fromResource(Endereco entity, EnderecoResource resource) {
		if (entity == null) {
			entity = new Endereco();
		}
		// inicializa os atributos da entidade.
		entity.setDataHoraCadastro(resource.getDataHoraCadastro());
		entity.setLat(resource.getLat());
		entity.setLng(resource.getLng());
		entity.setLogradouro(resource.getLogradouro());
		entity.setNumero(resource.getNumero());
		entity.setComplemento(resource.getComplemento());
		entity.setCep(resource.getCep());
		entity.setPontoReferencia(resource.getPontoReferencia());
		// inicializa entidades relacionadas
		Cidade cidade = loadEntityRelation(entity.getCidade(), resource.getCidade(), cidadeDao);
		entity.setCidade(cidade);
		Bairro bairro = loadEntityRelation(entity.getBairro(), resource.getBairro(), bairroDao);
		entity.setBairro(bairro);
		Uf uf = loadEntityRelation(entity.getUf(), resource.getUf(), ufDao);
		entity.setUf(uf);
		Pais pais = loadEntityRelation(entity.getPais(), resource.getPais(), paisDao);
		entity.setPais(pais);
		return entity;
	}

	@Override
	protected EnderecoResource toResource(Endereco entity) {
		return new EnderecoResource(entity);
	}
}