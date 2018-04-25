package br.com.framework.pilotojee7.relatorio.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.relatorio.dao.FiltroDao;
import br.com.framework.pilotojee7.relatorio.dao.RelatorioDao;
import br.com.framework.pilotojee7.relatorio.dao.RelatorioFiltroDao;
import br.com.framework.pilotojee7.relatorio.domain.Filtro;
import br.com.framework.pilotojee7.relatorio.domain.Relatorio;
import br.com.framework.pilotojee7.relatorio.domain.RelatorioFiltro;
import br.com.framework.pilotojee7.relatorio.manager.RelatorioFiltroManager;
import br.com.framework.pilotojee7.relatorio.service.resource.RelatorioFiltroResource;
import br.com.framework.service.impl.BaseEntityResourceEndpointImpl;

/**
 * Endpoint de {@link RelatorioFiltro}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/relatorioFiltro")
public class RelatorioFiltroEndpoint extends BaseEntityResourceEndpointImpl<Long, RelatorioFiltro, RelatorioFiltroResource, RelatorioFiltroDao, RelatorioFiltroManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private RelatorioDao relatorioDao;
	@Inject
	private RelatorioEndpoint relatorioEndpoint;
	@Inject
	private FiltroDao filtroDao;
	@Inject
	private FiltroEndpoint filtroEndpoint;
	
	@Override
	@Inject
	protected void setManager(RelatorioFiltroManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(RelatorioFiltroDao search) {
		super.setSearch(search);
	}

	@Override
	public RelatorioFiltro fromResource(RelatorioFiltro entity, RelatorioFiltroResource resource) {
		if (entity == null) {
			entity = new RelatorioFiltro();
		}
		// inicializa os atributos da entidade.
		entity.setRotulo(resource.getRotulo());
		entity.setOrdem(resource.getOrdem());
		entity.setPermissao(resource.getPermissao());
		entity.setHabilitado(resource.getHabilitado());
		entity.setParametro(resource.getParametro());
		entity.setDependenteDe(resource.getDependenteDe());
		// inicializa entidades relacionadas
		Relatorio relatorio = loadEntityRelation(entity.getRelatorio(), resource.getRelatorio(), relatorioDao);
		entity.setRelatorio(relatorio);
		Filtro filtro = loadEntityRelation(entity.getFiltro(), resource.getFiltro(), filtroDao);
		entity.setFiltro(filtro);
		// inicializa entidades relacionadas
		
		return entity;
	}

	@Override
	protected RelatorioFiltroResource toResource(RelatorioFiltro entity) {
		return new RelatorioFiltroResource(entity);
	}
}