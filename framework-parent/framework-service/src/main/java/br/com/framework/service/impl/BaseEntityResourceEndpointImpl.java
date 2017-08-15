/**
 * 
 */
package br.com.framework.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.OptimisticLockException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.exception.ExceptionUtils;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.model.dao.api.BaseDao;
import br.com.framework.model.exception.ModelException;
import br.com.framework.model.manager.api.BaseManager;
import br.com.framework.search.api.Search;
import br.com.framework.search.api.SearchResult;
import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.search.impl.Ordering;
import br.com.framework.search.impl.Ordering.Order;
import br.com.framework.search.util.SearchUtil;
import br.com.framework.service.api.BaseEntityResource;
import br.com.framework.service.api.BaseEntityResourceEndpoint;
import br.com.framework.service.api.BaseResource;
import br.com.framework.service.api.Error;
import br.com.framework.service.api.PaginatedResourceResponse;
import br.com.framework.service.util.LoadRelatedEntityResource;
import br.com.framework.service.util.UtilBuilder;

/**
 * Implementação abstrata para fornecimento de API WebService dos CRUDs de entitys.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public abstract class BaseEntityResourceEndpointImpl<PK extends Serializable, E extends BaseEntity<PK>, R extends BaseEntityResource<PK, E>, B extends Search<PK, E>, Manager extends BaseManager<PK, E, B>>
	extends BaseResourceEndpointImpl<R> implements BaseEntityResourceEndpoint<PK, E, R> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected B search;
	protected Manager manager;
	
	public BaseEntityResourceEndpointImpl() {
		super();
	}

	public BaseEntityResourceEndpointImpl(String bundleMessagesName) {
		super(bundleMessagesName);
	}

	/**
	 * @return the search
	 */
	protected B getSearch() {
		return search;
	}

	/**
	 * @param search the search to set
	 */
	protected void setSearch(B search) {
		this.search = search;
	}

	/**
	 * @return the negocio
	 */
	protected Manager getManager() {
		return manager;
	}

	/**
	 * @param negocio the negocio to set
	 */
	protected void setManager(Manager manager) {
		this.manager = manager;
	}
	
	/**
	 * Converte um {@link BaseResource} para uma {@link BaseEntity}.
	 * 
	 * Este método deve ser implementado na classe concreta.
	 * 
	 * @param entity - caso seja nulo, deve ser criado um novo objeto.
	 * @param resource
	 * @return
	 */
	protected E fromResource(E entity, R resource){
		entity.setId(resource.getId());
		entity.setVersaoEntidade(resource.getVersaoEntidade());
		return entity;
	}
	
	/**
	 * Converte uma {@link BaseEntity} para um {@link BaseResource}
	 * 
	 * Este método deve ser implementado na classe concreta.
	 * 
	 * @param entity
	 * @return
	 */
	protected abstract R toResource(E entity);
	
	/**
	 * Método responsável por carregar uma coleção de entidades relacionadas.
	 * 
	 * @param entityList
	 * @param resourceList
	 * @param search
	 */
	protected <ThePK extends Serializable, TheE extends BaseEntity<ThePK>, TheR extends BaseEntityResource<ThePK, TheE>, TheB extends Search<ThePK, TheE>> 
		void loadEntityRelations(Collection<TheE> entityList, Collection<TheR> resourceList, TheB search) {
		loadEntityRelations(entityList, resourceList, search, null);
	}
	
	/**
	 * Método responsável por carregar uma coleção de entidades relacionadas.
	 * 
	 * @param entityList
	 * @param resourceList
	 * @param search
	 * @param loaderRelatedEntity
	 */
	protected <ThePK extends Serializable, ThePE extends BaseEntity<ThePK>, TheE extends BaseEntity<ThePK>, TheR extends BaseEntityResource<ThePK, TheE>, TheB extends Search<ThePK, TheE>> 
		void loadEntityRelations(Collection<TheE> entityList, Collection<TheR> resourceList, TheB search, LoadRelatedEntityResource<ThePE, TheE, TheR> loaderRelatedEntity) {
		Iterator<TheE> itEntities = entityList.iterator();
		while (itEntities.hasNext()) {
			boolean found = false;
			TheE entity = itEntities.next();
			Iterator<TheR> itResources = resourceList.iterator();
			while (itResources.hasNext()) {
				TheR resource = itResources.next();
				if (resource.getId() != null && resource.getId().equals(entity.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				itEntities.remove();
			}
		}
		Iterator<TheR> itResources = resourceList.iterator();
		while (itResources.hasNext()) {
			boolean found = false;
			TheR resource = itResources.next();
			itEntities = entityList.iterator();
			while (itEntities.hasNext()) {
				TheE entity = itEntities.next();
				if (resource.getId() != null && resource.getId().equals(entity.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				TheE entity = null;
				if (resource.getId() != null) {
					entity = search.findById(resource.getId()).getUniqueResult();
				}
				if (loaderRelatedEntity != null) {
					entity = loaderRelatedEntity.loadRelatedEntityResource(entity, resource);
				}
				if (entity != null && !entityList.contains(entity)) {
					entityList.add(entity);
				}
			}
		}
	}
	
	/**
	 * Método responsável por carregar uma entidade relacionada.
	 * 
	 * @param entity
	 * @param resource
	 * @param search
	 * @return
	 */
	protected <ThePK extends Serializable, ThePE extends BaseEntity<ThePK>, TheE extends BaseEntity<ThePK>, TheR extends BaseEntityResource<ThePK, TheE>, TheB extends Search<ThePK, TheE>> 
		TheE loadEntityRelation(TheE entity, TheR resource, TheB search) {
		return loadEntityRelation(entity, resource, search, null);
	}
	
	/**
	 * Método responsável por carregar uma entidade relacionada.
	 * 
	 * @param entity
	 * @param resource
	 * @param search
	 * @param loaderRelatedEntity
	 * @return
	 */
	protected <ThePK extends Serializable, ThePE extends BaseEntity<ThePK>, TheE extends BaseEntity<ThePK>, TheR extends BaseEntityResource<ThePK, TheE>, TheB extends Search<ThePK, TheE>> 
		TheE loadEntityRelation(TheE entity, TheR resource, TheB search, LoadRelatedEntityResource<ThePE, TheE, TheR> loaderRelatedEntity) {
		TheE theE = null;
		if (resource != null) {
			if (resource.getId() != null) {
				theE = search.findById(resource.getId()).getUniqueResult();
			}
			if (loaderRelatedEntity != null) {
				theE = loaderRelatedEntity.loadRelatedEntityResource(theE, resource);
			}
		}
		return theE;
	}
	
	/**
	 * Método responsável por carregar uma entidade.
	 * 
	 * @param resource
	 * @param dao
	 * @param endpoint
	 * @return
	 */
	protected <TypePK extends Serializable, TypeE extends BaseEntity<TypePK>, TypeR extends BaseEntityResource<TypePK, TypeE>, TypeDao extends BaseDao<TypePK, TypeE>, TypeManager extends BaseManager<TypePK, TypeE, TypeDao>> TypeE 
		loadEntity(TypeR resource, TypeDao dao, BaseEntityResourceEndpointImpl<TypePK, TypeE, TypeR, TypeDao, TypeManager> endpoint) {
		TypeE entity = null;
		if (resource.getId() != null) {
			entity = dao.findById(resource.getId()).getUniqueResult();
		} else {
			entity = endpoint.fromResource(entity, resource);
		}
		return entity;
	}
	
	/**
	 * Converte uma {@link List} de {@link BaseEntity} para uma {@link List} de {@link BaseResource}
	 * 
	 * @param itemList
	 * @return
	 */
	protected List<R> toResources(List<E> itemList) {
		List<R> list = new ArrayList<R>();
		for (E i : itemList) {
			list.add(toResource(i));
		}
		return list;
	}
	
	/**
	 * Converte uma {@link List} de {@link BaseResource} para uma {@link List} de {@link BaseEntity}
	 * 
	 * @param itemList
	 * @return
	 */
	protected List<E> fromResources(List<R> itemList) {
		List<E> list = new ArrayList<E>();
		for (R i : itemList) {
			list.add(fromResource(null, i));
		}
		return list;
	}
	
	/**
     * Cria uma {@link List} de Erros com as constrains violadas.
     * 
     * @param violations
     * @return
     */
    protected List<Error> createViolationErrors(Set<ConstraintViolation<?>> violations) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("Validation completed. violations found: " + violations.size());
    	}
        List<Error> errors = new ArrayList<Error>();
        for (ConstraintViolation<?> violation : violations) {
        	errors.add(UtilBuilder.buildError(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return errors;
    }
    
    /**
     * Cria uma {@link List} de Erros com as constrains violadas.
     * 
     * @param ex
     * @return
     */
    protected List<Error> createViolationErrors(ConstraintViolationException ex) {
        return createViolationErrors(ex.getConstraintViolations());
    }
    
    
	@Override
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response insert(R resource) {
		if (resource == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		E entity = fromResource(null, resource);
		Response response = null;
        try {
        	entity = getManager().insert(entity);
            response = Response.created(UriBuilder.fromResource(getClass())
    				.path(String.valueOf(entity.getId())).build()).build();
        } catch (ConstraintViolationException ce) {
        	response = Response.status(Status.BAD_REQUEST).entity(createViolationErrors(ce)).build();
        } catch (ModelException me) {
        	response = Response.status(Status.BAD_REQUEST).entity(createModelErrors(me)).build();
        } catch (EJBTransactionRolledbackException e) {
        	logger.error("Error ao inserir resource da entity: " + entity.getClass(), e);
        	Exception causedByException = e.getCausedByException();
        	Throwable rootCause = ExceptionUtils.getRootCause(e);
        	if (rootCause instanceof ConstraintViolationException) {
        		response = Response.status(Status.BAD_REQUEST).entity(createViolationErrors((ConstraintViolationException)rootCause)).build();
        	} else if (rootCause instanceof ModelException) {
        		response = Response.status(Status.BAD_REQUEST).entity(createModelErrors((ModelException)rootCause)).build();
        	} else {
    			response = Response.status(Status.BAD_REQUEST).entity(createGenericError(causedByException)).build();
    		}
        } catch (Exception e) {
        	logger.error("Error ao inserir resource da entity: " + entity.getClass(), e);
        	if (e.getCause() instanceof ConstraintViolationException) {
				response = Response.status(Status.BAD_REQUEST).entity(
					createViolationErrors((ConstraintViolationException) e.getCause())).build();
			} else if (e.getCause() instanceof ModelException) {
				response = Response.status(Status.BAD_REQUEST).entity(
					createModelErrors((ModelException)e.getCause())).build();
			} else{
				response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(
					createGenericError(e)).build();
			}
		}
        return response;
	}

	@Override
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response update(@PathParam("id") PK id, R resource) {
		if (resource == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		SearchUniqueResult<E> findById = getSearch().findById(id);
		if (findById.getUniqueResult() == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		E entity = fromResource(findById.getUniqueResult(), resource);
		Response response = null;
        try {
        	entity = getManager().update(entity);
            response = Response.noContent().build();
        } catch (ConstraintViolationException ce) {
        	response = Response.status(Status.BAD_REQUEST).entity(createViolationErrors(ce)).build();
        } catch (OptimisticLockException e) {
        	response = Response.status(Status.CONFLICT).entity(e.getEntity()).build();
		} catch (ModelException me) {
        	response = Response.status(Status.BAD_REQUEST).entity(createModelErrors(me)).build();
        } catch (EJBTransactionRolledbackException e) {
        	logger.error("Error ao editar resource da entity: " + entity.getClass(), e);
        	Exception causedByException = e.getCausedByException();
        	Throwable rootCause = ExceptionUtils.getRootCause(e);
        	if (rootCause instanceof ConstraintViolationException) {
        		response = Response.status(Status.BAD_REQUEST).entity(createViolationErrors((ConstraintViolationException)rootCause)).build();
        	} else if (rootCause instanceof ModelException) {
        		response = Response.status(Status.BAD_REQUEST).entity(createModelErrors((ModelException)rootCause)).build();
        	} else {
    			response = Response.status(Status.BAD_REQUEST).entity(createGenericError(causedByException)).build();
    		}
        } catch (Exception e) {
        	logger.error("Error ao editar resource da entity: " + findById.getUniqueResult().getClass(), e);
			if (e.getCause() instanceof ConstraintViolationException) {
				response = Response.status(Status.BAD_REQUEST).entity(
					createViolationErrors((ConstraintViolationException) e.getCause())).build();
			} else if (e.getCause() instanceof ModelException) {
				response = Response.status(Status.BAD_REQUEST).entity(
					createModelErrors((ModelException)e.getCause())).build();
			} else{
				response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(
					createGenericError(e)).build();
			}
		}
		return response;
	}

	@Override
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response remove(@PathParam("id") PK id) {
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		SearchUniqueResult<E> findById = getSearch().findById(id);
		if (findById.getUniqueResult() == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Response response = null;
		try {
			getManager().remove(findById.getUniqueResult());
			response = Response.noContent().build();
        } catch (ConstraintViolationException ce) {
        	response = Response.status(Status.BAD_REQUEST).entity(createViolationErrors(ce)).build();
        } catch (OptimisticLockException e) {
        	response = Response.status(Status.CONFLICT).entity(e.getEntity()).build();
		} catch (ModelException me) {
        	response = Response.status(Status.BAD_REQUEST).entity(createModelErrors(me)).build();
        } catch (EJBTransactionRolledbackException e) {
        	logger.error("Error ao remover resource da entity id: " + id, e);
        	Exception causedByException = e.getCausedByException();
        	Throwable rootCause = ExceptionUtils.getRootCause(e);
        	if (rootCause instanceof ConstraintViolationException) {
        		response = Response.status(Status.BAD_REQUEST).entity(createViolationErrors((ConstraintViolationException)rootCause)).build();
        	} else if (rootCause instanceof ModelException) {
        		response = Response.status(Status.BAD_REQUEST).entity(createModelErrors((ModelException)rootCause)).build();
        	} else {
    			response = Response.status(Status.BAD_REQUEST).entity(createGenericError(causedByException)).build();
    		}
        } catch (Exception e) {
        	logger.error("Error ao remover resource da entity: " + findById.getUniqueResult().getClass(), e);
			if (e.getCause() instanceof ConstraintViolationException) {
				response = Response.status(Status.BAD_REQUEST).entity(
					createViolationErrors((ConstraintViolationException) e.getCause())).build();
			} else if (e.getCause() instanceof ModelException) {
				response = Response.status(Status.BAD_REQUEST).entity(
					createModelErrors((ModelException)e.getCause())).build();
			} else{
				response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(
					createGenericError(e)).build();
			}
		}
		return response;
	}

	@Override
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response findById(@PathParam("id") PK id) {
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		SearchUniqueResult<E> findById = getSearch().findById(id);
		if (findById.getUniqueResult() == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		R resource = toResource(findById.getUniqueResult());
		return Response.ok(resource).build();
	}

	@Override
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PaginatedResourceResponse<R> findAll(
			@QueryParam("first") @DefaultValue("-1") Integer first, 
			@QueryParam("max") @DefaultValue("-1") Integer max,
			@QueryParam("fieldOrder") String fieldOrder, 
			@QueryParam("order") Order order) {
		Ordering ordering = null;
		if (fieldOrder != null) {
			ordering = SearchUtil.instance().order(fieldOrder, order != null ? order : Order.ASC);
		}
		PaginatedResourceResponse<R> response = new PaginatedResourceResponseImpl<R>();
		SearchUniqueResult<Long> countFindAll = getSearch().getCountFindAll();
		if (countFindAll != null && countFindAll.getUniqueResult() != null) {
			response.setTotalRecords(countFindAll.getUniqueResult());
		}
		SearchResult<E> findAll = getSearch().findAll(first, max, ordering);
		if (findAll != null && !findAll.getResults().isEmpty()) {
			response.setResults(toResources(findAll.getResults()));
		}
		return response;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.service.api.BaseResourceEndpoint#getCountTodos()
	 */
	@Override
	@GET
    @Path("/count")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Long getCountFindAll() {
		SearchUniqueResult<Long> countFindAll = getSearch().getCountFindAll();
		Long result = 0L;
		if (countFindAll != null) {
			result = countFindAll.getUniqueResult();
		}
		return result;
	}

	@Override
	@POST
    @Path("/findByRestrictions")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PaginatedResourceResponse<R> findByRestrictions(
			FindByRestrictionsRequest request) {
		PaginatedResourceResponse<R> response = new PaginatedResourceResponseImpl<R>();
		SearchUniqueResult<Long> countFindByRestrictions = getSearch().getCountFindByRestrictions(request.getRestrictions());
		if (countFindByRestrictions != null && countFindByRestrictions.getUniqueResult() != null) {
			response.setTotalRecords(countFindByRestrictions.getUniqueResult());
		}
		SearchResult<E> findByRestrictions = getSearch().findByRestrictions(request.getRestrictions(), 
				request.getFirst(), request.getMax(), 
				request.getOrderings().toArray(new Ordering[0]));
		if (findByRestrictions != null && !findByRestrictions.getResults().isEmpty()) {
			response.setResults(toResources(findByRestrictions.getResults()));
		}
		return response;
	}

	@Override
	@POST
    @Path("/countFindByRestrictions")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Long getCountFindByRestrictions(FindByRestrictionsRequest request) {
		SearchUniqueResult<Long> countFindByRestrictions = getSearch().getCountFindByRestrictions(request.getRestrictions());
		Long result = 0L;
		if (countFindByRestrictions != null) {
			result = countFindByRestrictions.getUniqueResult();
		}
		return result;
	}

}
