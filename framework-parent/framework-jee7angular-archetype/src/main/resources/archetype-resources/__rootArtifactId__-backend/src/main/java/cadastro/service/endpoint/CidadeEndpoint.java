#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import ${package}.cadastro.dao.CidadeDao;
import ${package}.cadastro.dao.UfDao;
import ${package}.cadastro.domain.Cidade;
import ${package}.cadastro.domain.Uf;
import ${package}.cadastro.manager.CidadeManager;
import ${package}.cadastro.service.resource.CidadeResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;

/**
 * Endpoint de {@link Cidade}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/cidade")
public class CidadeEndpoint extends BaseEntityResourceEndpointImpl<Long, Cidade, CidadeResource, CidadeDao, CidadeManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private UfDao ufDao;
	
	@Override
	@Inject
	protected void setManager(CidadeManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(CidadeDao search) {
		super.setSearch(search);
	}

	@Override
	public Cidade fromResource(Cidade entity, CidadeResource resource) {
		if (entity == null) {
			entity = new Cidade();
		}
		// inicializa os atributos da entidade.
		entity.setNome(resource.getNome());
		entity.setCodigoIbge(resource.getCodigoIbge());
		// inicializa entidades relacionadas
		Uf uf = loadEntityRelation(entity.getUf(), resource.getUf(), ufDao);
		entity.setUf(uf);
		return entity;
	}

	@Override
	protected CidadeResource toResource(Cidade entity) {
		return new CidadeResource(entity);
	}
}