package br.com.framework.pilotojee7.cadastro.service.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.framework.pilotojee7.backend.keycloak.dao.KeycloakGrupoDao;
import br.com.framework.pilotojee7.backend.keycloak.service.endpoint.KeycloakGrupoEndpoint;
import br.com.framework.pilotojee7.cadastro.dao.CategoriaNoticiaDao;
import br.com.framework.pilotojee7.cadastro.dao.ImagemDao;
import br.com.framework.pilotojee7.cadastro.dao.NoticiaDao;
import br.com.framework.pilotojee7.cadastro.domain.CategoriaNoticia;
import br.com.framework.pilotojee7.cadastro.domain.Imagem;
import br.com.framework.pilotojee7.cadastro.domain.Noticia;
import br.com.framework.pilotojee7.cadastro.manager.NoticiaManager;
import br.com.framework.pilotojee7.cadastro.service.resource.CategoriaNoticiaResource;
import br.com.framework.pilotojee7.cadastro.service.resource.ImagemResource;
import br.com.framework.pilotojee7.cadastro.service.resource.NoticiaResource;
import br.com.framework.service.impl.BaseEntityAuditedResourceEndpointImpl;
import br.com.framework.service.util.LoadRelatedEntityResource;

/**
 * Endpoint de {@link Noticia}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Stateless
@Path("/noticia")
public class NoticiaEndpoint extends BaseEntityAuditedResourceEndpointImpl<Long, Noticia, NoticiaResource, NoticiaDao, NoticiaManager>{

	private static final long serialVersionUID = -1L;
	
	@Inject
	private CategoriaNoticiaDao categoriaNoticiaDao;
	@Inject
	private CategoriaNoticiaEndpoint categoriaNoticiaEndpoint;
	@Inject
	private ImagemDao imagemDao;
	@Inject
	private ImagemEndpoint imagemEndpoint;
	@Inject
	private KeycloakGrupoDao keycloakGrupoDao;
	@Inject
	private KeycloakGrupoEndpoint keycloakGrupoEndpoint;
	
	@Override
	@Inject
	protected void setManager(NoticiaManager manager) {
		super.setManager(manager);
	}
	
	@Override
	@Inject
	protected void setSearch(NoticiaDao search) {
		super.setSearch(search);
	}

	@Override
	public Noticia fromResource(Noticia entity, NoticiaResource resource) {
		if (entity == null) {
			entity = new Noticia();
		}
		// inicializa os atributos da entidade.
		entity.setTitulo(resource.getTitulo());
		entity.setConteudo(resource.getConteudo());
		entity.setDescricao(resource.getDescricao());
		entity.setDataInicial(resource.getDataInicial());
		entity.setDataFinal(resource.getDataFinal());
		entity.setDestaque(resource.getDestaque());
		// inicializa entidades relacionadas
		loadEntityRelations(entity.getImagens(), resource.getImagens(), imagemDao, new LoadRelatedEntityResource<Noticia, Imagem, ImagemResource>(entity) {
			@Override
			public Imagem loadRelatedEntityResource(Imagem relatedEntity,
					ImagemResource relatedResource) {
				relatedEntity = imagemEndpoint.fromResource(relatedEntity, relatedResource);
				return relatedEntity;
			}
		});

		loadEntityRelations(entity.getCategorias(), resource.getCategorias(), categoriaNoticiaDao, new LoadRelatedEntityResource<Noticia, CategoriaNoticia, CategoriaNoticiaResource>(entity) {
			@Override
			public CategoriaNoticia loadRelatedEntityResource(CategoriaNoticia relatedEntity, CategoriaNoticiaResource relatedResource) {
				relatedEntity = categoriaNoticiaEndpoint.fromResource(relatedEntity, relatedResource);
				return relatedEntity;
			}
		});

		loadEntityRelations(entity.getGrupos(), resource.getGrupos(), keycloakGrupoDao);
		return entity;
	}

	protected List<NoticiaResource> toResources(List<Noticia> itemList, boolean onlyId, boolean loadBase64) {
		List<NoticiaResource> list = new ArrayList<NoticiaResource>();
		for (Noticia i : itemList) {
			list.add(toResource(i, onlyId, loadBase64));
		}
		return list;
	}

	protected NoticiaResource toResource(Noticia entity, boolean onlyId, boolean loadData) {
		return new NoticiaResource(entity, onlyId, loadData);
	}

	@Override
	protected NoticiaResource toResource(Noticia entity) {
		return new NoticiaResource(entity, false, true);
	}
	/*
	@Override
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response findById(Long id) {
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		SearchUniqueResult<Noticia> findById = getSearch().findById(id);
		if (findById.getUniqueResult() == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		NoticiaResource resource = toResource(findById.getUniqueResult(), false, true);
		return Response.ok(resource).build();
	}*/
}