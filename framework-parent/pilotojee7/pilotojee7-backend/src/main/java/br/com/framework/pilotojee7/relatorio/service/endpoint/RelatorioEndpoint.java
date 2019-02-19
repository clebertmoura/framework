package br.com.framework.pilotojee7.relatorio.service.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.apache.commons.codec.binary.Base64;

import br.com.framework.pilotojee7.relatorio.dao.CategoriaDao;
import br.com.framework.pilotojee7.relatorio.dao.RelatorioDao;
import br.com.framework.pilotojee7.relatorio.dao.RelatorioFiltroDao;
import br.com.framework.pilotojee7.relatorio.domain.Categoria;
import br.com.framework.pilotojee7.relatorio.domain.Relatorio;
import br.com.framework.pilotojee7.relatorio.domain.RelatorioFiltro;
import br.com.framework.pilotojee7.relatorio.manager.RelatorioManager;
import br.com.framework.pilotojee7.relatorio.service.resource.RelatorioFiltroResource;
import br.com.framework.pilotojee7.relatorio.service.resource.RelatorioResource;
import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;
import br.com.framework.service.util.LoadRelatedEntityResource;

/**
 * Endpoint de {@link Relatorio}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/relatorio")
public class RelatorioEndpoint extends BaseEntityAuditedResourceEndpointImpl<Long, Relatorio, RelatorioResource, RelatorioDao, RelatorioManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private RelatorioFiltroDao relatorioFiltroDao;
	@Inject
	private RelatorioFiltroEndpoint relatorioFiltroEndpoint;
	@Inject
	private CategoriaDao categoriaDao;
	@Inject
	private CategoriaEndpoint categoriaEndpoint;
	
	@Override
	@Inject
	protected void setManager(RelatorioManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(RelatorioDao search) {
		super.setSearch(search);
	}

	@Override
	public Relatorio fromResource(Relatorio entity, RelatorioResource resource) {
		if (entity == null) {
			entity = new Relatorio();
		}
		// inicializa os atributos da entidade.
		entity.setNome(resource.getNome());
		entity.setDescricao(resource.getDescricao());
		entity.setPermissao(resource.getPermissao());
		entity.setHabilitado(resource.getHabilitado());
		byte[] data = null;
		if (resource.getJrxmlData() != null && resource.getJrxmlData().indexOf(";base64") > -1) {
			data = Base64.decodeBase64(resource.getJrxmlData().substring(resource.getJrxmlData().indexOf("base64,") + 7));
			entity.setJrxmlData(data);
		}
		// inicializa entidades relacionadas
		loadEntityRelations(entity.getFiltros(), resource.getFiltros(), relatorioFiltroDao, new LoadRelatedEntityResource<Relatorio, RelatorioFiltro, RelatorioFiltroResource>(entity) {
			@Override
			public RelatorioFiltro loadRelatedEntityResource(RelatorioFiltro relatedEntity,
					RelatorioFiltroResource relatedResource) {
				relatedEntity = relatorioFiltroEndpoint.fromResource(relatedEntity, relatedResource);
				relatedEntity.setRelatorio(getParentEntity());
				return relatedEntity;
			}
		});
		Categoria categoria = loadEntityRelation(entity.getCategoria(), resource.getCategoria(), categoriaDao);
		entity.setCategoria(categoria);
		return entity;
	}

	@Override
	protected RelatorioResource toResource(Relatorio entity) {
		return new RelatorioResource(entity);
	}
}