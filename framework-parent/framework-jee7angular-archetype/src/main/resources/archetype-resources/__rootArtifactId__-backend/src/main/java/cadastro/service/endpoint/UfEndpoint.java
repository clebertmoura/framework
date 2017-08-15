#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cadastro.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import ${package}.cadastro.dao.PaisDao;
import ${package}.cadastro.dao.UfDao;
import ${package}.cadastro.domain.Pais;
import ${package}.cadastro.domain.Uf;
import ${package}.cadastro.manager.UfManager;
import ${package}.cadastro.service.resource.UfResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;

/**
 * Endpoint de {@link Uf}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/uf")
public class UfEndpoint extends BaseEntityResourceEndpointImpl<Long, Uf, UfResource, UfDao, UfManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private PaisDao paisDao;
	
	@Override
	@Inject
	protected void setManager(UfManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(UfDao search) {
		super.setSearch(search);
	}

	@Override
	public Uf fromResource(Uf entity, UfResource resource) {
		if (entity == null) {
			entity = new Uf();
		}
		// inicializa os atributos da entidade.
		entity.setNome(resource.getNome());
		entity.setSigla(resource.getSigla());
		entity.setCodigoIbge(resource.getCodigoIbge());
		// inicializa entidades relacionadas
		Pais pais = loadEntityRelation(entity.getPais(), resource.getPais(), paisDao);
		entity.setPais(pais);
		return entity;
	}

	@Override
	protected UfResource toResource(Uf entity) {
		return new UfResource(entity);
	}
}