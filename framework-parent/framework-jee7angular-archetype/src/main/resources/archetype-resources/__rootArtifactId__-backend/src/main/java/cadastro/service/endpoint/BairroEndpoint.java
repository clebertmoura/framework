#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import ${package}.cadastro.dao.BairroDao;
import ${package}.cadastro.dao.CidadeDao;
import ${package}.cadastro.domain.Bairro;
import ${package}.cadastro.domain.Cidade;
import ${package}.cadastro.manager.BairroManager;
import ${package}.cadastro.service.resource.BairroResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;

/**
 * Endpoint de {@link Bairro}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/bairro")
public class BairroEndpoint extends BaseEntityResourceEndpointImpl<Long, Bairro, BairroResource, BairroDao, BairroManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private CidadeDao cidadeDao;
	
	@Override
	@Inject
	protected void setManager(BairroManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(BairroDao search) {
		super.setSearch(search);
	}

	@Override
	public Bairro fromResource(Bairro entity, BairroResource resource) {
		if (entity == null) {
			entity = new Bairro();
		}
		// inicializa os atributos da entidade.
		entity.setNome(resource.getNome());
		// inicializa entidades relacionadas
		Cidade cidade = loadEntityRelation(entity.getCidade(), resource.getCidade(), cidadeDao);
		entity.setCidade(cidade);
		return entity;
	}

	@Override
	protected BairroResource toResource(Bairro entity) {
		return new BairroResource(entity);
	}
}