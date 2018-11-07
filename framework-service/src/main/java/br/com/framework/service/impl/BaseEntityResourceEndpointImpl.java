/**
 * 
 */
package br.com.framework.service.impl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJBException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Lob;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import br.com.framework.model.dao.api.BaseDao;
import br.com.framework.model.exception.ModelException;
import br.com.framework.model.manager.api.BaseManager;
import br.com.framework.search.api.Search;
import br.com.framework.search.api.SearchResult;
import br.com.framework.search.api.SearchUniqueResult;
import br.com.framework.search.impl.Ordering;
import br.com.framework.search.impl.PageRequest;
import br.com.framework.search.impl.PageResponse;
import br.com.framework.search.impl.Ordering.Order;
import br.com.framework.search.util.SearchUtil;
import br.com.framework.service.api.BaseEntityResource;
import br.com.framework.service.api.BaseEntityResourceEndpoint;
import br.com.framework.service.api.BaseResource;
import br.com.framework.service.api.Error;
import br.com.framework.service.api.PaginatedResourceResponse;
import br.com.framework.service.util.LoadRelatedEntityResource;
import br.com.framework.service.util.UtilBuilder;
import br.com.framework.domain.api.BaseEntity;
import br.com.framework.util.reflection.ReflectionUtils;

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

	private static final String HEADER_CONTENT_TYPE = "Content-Type";
	private static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

	private static final String ATTR_SUFIX_FILENAME = "FileName";
	private static final String ATTR_SUFIX_CONTENTTYPE = "ContentType";
	private static final String ATTR_SUFIX_BINARY = "Binary";
	private static final String ATTR_SUFIX_SIZE = "Size";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected B search;
	protected Manager manager;
	
	protected Class<PK> entityPKClass;
	protected Class<E> entityClass;
	protected Class<R> entityResourceClass;
	
	/**
	 * @param entityPKClass
	 * @param entityClass
	 * @param entityResourceClass
	 */
	public BaseEntityResourceEndpointImpl(Class<PK> entityPKClass, Class<E> entityClass, Class<R> entityResourceClass) {
		super();
		this.entityPKClass = entityPKClass;
		this.entityClass = entityClass;
		this.entityResourceClass = entityResourceClass;
	}

	public BaseEntityResourceEndpointImpl(String bundleMessagesName) {
		super(bundleMessagesName);
	}

	protected Class<PK> getEntityPKClass() {
		return entityPKClass;
	}

	protected Class<E> getEntityClass() {
		return entityClass;
	}

	protected Class<R> getEntityResourceClass() {
		return entityResourceClass;
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
	 * @param resource
	 * @return
	 */
	public E fromResource(R resource){
		return fromResource(resource, 1);
	}
	
	/**
	 * Converte um {@link BaseResource} para uma {@link BaseEntity}.
	 * 
	 * @param resource
	 * @param depth
	 * @return
	 */
	public E fromResource(R resource, int depth){
		return fromResource(resource, null, depth);
	}
	
	/**
	 * Converte um {@link BaseResource} para uma {@link BaseEntity}.
	 * 
	 * @param resource
	 * 	Objeto no qual será setado os atributos. Caso seja nulo, será criado um novo objeto.
	 * @param entity
	 * @return
	 */
	public E fromResource(R resource, E entity){
		return fromResource(resource, entity, 1);
	}
	
	/**
	 * Converte um {@link BaseResource} para uma {@link BaseEntity}.
	 * 
	 * @param resource
	 * @param entity
	 * Indica a profundidade da serialização. 
	 * 	- Um depth igual a 0, significa que associações x-to-one não serão serializadas.
	 * 	- Um depth igual a 1, significa que associações x-to-one serão serializadas.
	 * 	- Um depth igual a 2, significa que associações x-to-one de associações x-to-one serão serializadas.
	 * 	- etc.
	 * @return
	 */
	public E fromResource(R resource, E entity, int depth){
		if (resource == null) {
            return null;
        }
		try {
			if (entity == null) {
				entity = getEntityClass().newInstance();
			}
		} catch (Exception e) {
			logger.error(String.format("Erro ao instanciar entidade: %s", getEntityClass().getSimpleName()), e);
			throw new RuntimeException(e);
		}
		entity.setId(resource.getId());
		entity.setVersion(resource.getVersion());
		return entity;
	}
	

	/**
	 * Converte uma {@link List} de {@link BaseResource} para uma {@link List} de {@link BaseEntity}
	 * 
	 * @param resourceList
	 * @return
	 */
	public List<E> fromResources(List<R> resourceList) {
		List<E> list = new ArrayList<E>();
		for (R i : resourceList) {
			list.add(fromResource(i, null));
		}
		return list;
	}
	
	/**
	 * Converte uma {@link List} de {@link BaseResource} para uma {@link List} de {@link BaseEntity}
	 * 
	 * @param resourceList
	 * @param depth
	 * @return
	 */
	public List<E> fromResources(List<R> resourceList, int depth) {
		List<E> list = new ArrayList<E>();
		for (R i : resourceList) {
			list.add(fromResource(i, null, depth));
		}
		return list;
	}
	
	/**
	 * Converte uma {@link BaseEntity} para um {@link BaseResource}
	 * 
	 * @param entity
	 * @return
	 */
	public R toResource(E entity){
		return toResource(entity, 1);
	}
	
	/**
	 * Converte uma {@link BaseEntity} para um {@link BaseResource}
	 * 
	 * @param entity
	 * @param depth
	 * @return
	 */
	public R toResource(E entity, int depth){
		return toResource(entity, null, depth);
	}
	
	/**
	 * Converte uma {@link BaseEntity} para um {@link BaseResource}
	 * 
	 * @param entity
	 * 	Objeto no qual será setado os atributos. Caso seja nulo, será criado um novo objeto.
	 * @param resource
	 * @return
	 */
	public R toResource(E entity, R resource){
		return toResource(entity, resource, 1);
	}
	
	/**
	 * Converte uma {@link BaseEntity} para um {@link BaseResource}
	 * 
	 * Deve ser sobrecarregado nas classes concretas, para inicializar os campos específicos.
	 * 
	 * @param entity
	 * @param depth 
	 * 	Indica a profundidade da serialização. 
	 * 	- Um depth igual a 0, significa que associações x-to-one não serão serializadas.
	 * 	- Um depth igual a 1, significa que associações x-to-one serão serializadas.
	 * 	- Um depth igual a 2, significa que associações x-to-one de associações x-to-one serão serializadas.
	 * 	- etc.
	 * 	
	 * @return
	 */
	public R toResource(E entity, R resource, int depth) {
		if (entity == null) {
            return null;
        }
		try {
			if (resource == null) {
				resource = getEntityResourceClass().newInstance();
			}
		} catch (Exception e) {
			logger.error(String.format("Erro ao instanciar resource: %s", getEntityResourceClass().getSimpleName()), e);
			throw new RuntimeException(e);
		}
		resource.setId(entity.getId());
		resource.setVersion(entity.getVersion());
		return resource;
	}
	
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
	 * Método responsável por carregar uma coleção de entidades relacionadas.
	 * 
	 * @param entityList
	 * @param resourceList
	 * @param depth
	 * @param search
	 * @param loaderRelatedEntity
	 */
	public <ThePK extends Serializable, ThePE extends BaseEntity<ThePK>, TheE extends BaseEntity<ThePK>, TheR extends BaseEntityResource<ThePK, TheE>, TheB extends Search<ThePK, TheE>> 
		void loadEntityRelations(final Collection<TheE> entityList, final Collection<TheR> resourceList, int depth, TheB search, LoadRelatedEntityResource<ThePE, TheE, TheR> loaderRelatedEntity) {
		
		entityList.stream().forEach(entity -> {
			boolean found = resourceList.stream().filter(
				resource -> resource.getId() != null && resource.getId().equals(entity.getId())).count() > 0;
			if (found) {
				entityList.remove(entity);
			}
		});
		
		
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
			entity = endpoint.fromResource(resource, entity);
		}
		return entity;
	}
	
	/**
	 * Converte uma {@link List} de {@link BaseEntity} para uma {@link List} de {@link BaseResource}
	 * 
	 * @param entityList
	 * @return
	 */
	public List<R> toResources(List<E> entityList) {
		List<R> list = new ArrayList<R>();
		for (E i : entityList) {
			list.add(toResource(i));
		}
		return list;
	}
	
	/**
	 * Converte uma {@link List} de {@link BaseEntity} para uma {@link List} de {@link BaseResource}. O parâmetro <b>depth</b> é usado para controlar a profundidade de associações a serem serializadas.
	 * Prevenindo um ciclo infinito de serialização 
     *
	 * @param entityList
	 * @param depth the depth of the serialization. A depth equals to 0, means no x-to-one association will be serialized.
     *              A depth equals to 1 means that xToOne associations will be serialized. 2 means, xToOne associations of
     *              xToOne associations will be serialized, etc.
	 * @return
	 */
	public List<R> toResources(List<E> entityList, int depth) {
		List<R> list = new ArrayList<R>();
		for (E i : entityList) {
			try {
				R resource = getEntityResourceClass().newInstance();
				list.add(toResource(i, resource, depth));
			} catch (Exception e) {
				logger.error(String.format("Erro ao instanciar o resource: %s", getEntityResourceClass().getSimpleName()), e);
				throw new RuntimeException(e);
			}
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
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Response insert(R resource) {
		if (resource == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		E entity = fromResource(resource);
		Response response = null;
        try {
        	entity = getManager().insert(entity);
            response = Response.created(UriBuilder.fromResource(getClass())
						.path(String.valueOf(entity.getId()))
						.build())
						.entity(toResource(entity))
						.build();
        } catch (ConstraintViolationException ce) {
        	response = Response.status(Status.BAD_REQUEST).entity(createViolationErrors(ce)).build();
        } catch (ModelException me) {
        	response = Response.status(Status.BAD_REQUEST).entity(createModelErrors(me)).build();
        } catch (EJBException e) {
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
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Response update(@PathParam("id") PK id, R resource) {
		if (resource == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		SearchUniqueResult<E> findById = getSearch().findById(id);
		if (findById.getUniqueResult() == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		E entity = fromResource(resource, findById.getUniqueResult());
		Response response = null;
        try {
        	entity = getManager().update(entity);
            response = Response.accepted(toResource(entity)).build();
        } catch (ConstraintViolationException ce) {
        	response = Response.status(Status.BAD_REQUEST).entity(createViolationErrors(ce)).build();
        } catch (OptimisticLockException e) {
        	response = Response.status(Status.CONFLICT).entity(e.getEntity()).build();
		} catch (ModelException me) {
        	response = Response.status(Status.BAD_REQUEST).entity(createModelErrors(me)).build();
        } catch (EJBException e) {
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
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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
        } catch (EJBException e) {
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
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response findById(@PathParam("id") PK id, @QueryParam("depth") @DefaultValue("1") Integer depth) {
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		SearchUniqueResult<E> findById = getSearch().findById(id);
		if (findById.getUniqueResult() == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		R resource = toResource(findById.getUniqueResult(), depth);
		return Response.ok(resource).build();
	}

	@Override
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PaginatedResourceResponse<R> findAll(
			@QueryParam("first") @DefaultValue("-1") Integer first, 
			@QueryParam("max") @DefaultValue("-1") Integer max,
			@QueryParam("fieldOrder") String fieldOrder, 
			@QueryParam("order") Order order,
			@QueryParam("depth") @DefaultValue("0") Integer depth) {
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
			response.setResults(toResources(findAll.getResults(), depth));
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
	
	@POST
    @Path("/findPage")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PageResponse<R> findPage(PageRequest pageRequest) {
		PageResponse<E> pageResponse = getSearch().findPage(pageRequest);
		PageResponse<R> response = new PageResponse<>(
				toResources(pageResponse.getResults(), pageRequest.getDepth()), pageResponse.getTotalRegisters());
		return response;
	}
	
	/**
	 * Responsável por disponibilizar o download do conteúdo (binário ou texto) de um atributo {@link Lob} da entidade.
	 * 
	 * @param id
	 * 	Chave primária da entidade.
	 * @param attribute
	 * 	Atributo {@link Lob}
	 * @param hh
	 * @param ui
	 * @return
	 */
	@GET
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@Path("/{id}/{attribute}/download")
	public Response attributeDownload(@PathParam("id") PK id, @PathParam("attribute") String attribute,
			@Context HttpHeaders hh, @Context UriInfo ui) {
		Response response = null;
		try {
			E entity = this.getSearch().findById(id).getUniqueResult();
			if (entity != null) {
				Field fieldAttribute = ReflectionUtils.getField(attribute, entityClass);
				if (fieldAttribute != null) {
					Method getterAttribute = ReflectionUtils.getFieldGetter(entityClass, fieldAttribute.getType(), attribute);
					if (getterAttribute != null && getterAttribute.isAnnotationPresent(Lob.class)) {
						Method getterContentType = null;
						Method getterFileName = null;
						if (attribute.endsWith(ATTR_SUFIX_BINARY)) {
							String attr = attribute.substring(0, attribute.indexOf(ATTR_SUFIX_BINARY));
							getterContentType = ReflectionUtils.getFieldGetter(entityClass, String.class, attr + ATTR_SUFIX_CONTENTTYPE);
							getterFileName = ReflectionUtils.getFieldGetter(entityClass, String.class, attr + ATTR_SUFIX_FILENAME);
						}
						Object returnAttribute = null;
						Object returnContentType = null;
						Object returnFileName = null;
						try {
							returnAttribute = getterAttribute.invoke(entity);
							if (getterContentType != null) {
								returnContentType = getterContentType.invoke(entity);
							}
							if (getterFileName != null) {
								returnFileName = getterFileName.invoke(entity);
							}
						} catch (Exception e) {
							logger.error("Erro ao executar metodo: " + getterAttribute.getName(), e);
						}
						if (returnAttribute != null) {
							boolean isBinary = byte[].class.equals(fieldAttribute.getType());
							final Object data = returnAttribute;
							final String contentType = returnContentType != null ? returnContentType.toString()
									: MediaType.APPLICATION_OCTET_STREAM;
							final String fileName = returnFileName != null ? returnFileName.toString() : id + ".dat";
							StreamingOutput stream = new StreamingOutput() {
								@Override
								public void write(OutputStream os) throws IOException, WebApplicationException {
									BufferedOutputStream baos = new BufferedOutputStream(os);
									if (isBinary) {
										baos.write((byte[]) data);
									} else {
										baos.write(((String)data).getBytes());
									}
									baos.flush();
								}
							};
							response = Response.ok(stream, contentType)
									.header(HEADER_CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").build();
						} else {
							response = Response.noContent().build();
						}
					} else {
						response = Response.status(Status.BAD_REQUEST).entity("No field accessor or @Lob not present.").build();
					}
				} else {
					response = Response.status(Status.BAD_REQUEST).entity("Field not found.").build();
				}
			} else {
				response = Response.status(Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
			logger.error(String.format("Erro ao realizar download do atributo: %s", attribute), e);
			response = Response.serverError().build();
		}
		
		return response;

	}
	
	
	/**
	 * Responsável por disponibilizar o upload do conteúdo (binário ou texto) de um atributo {@link Lob} da entidade.
	 * 
	 * @param id
	 * 	Chave primária da entidade.
	 * @param attribute
	 * 	Atributo {@link Lob}
	 * @param hh
	 * @param ui
	 * @param input
	 * @return
	 */
	@POST
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Path("/{id}/{attribute}/upload")
	public Response attributeUpload(
			@PathParam("id") PK id,
            @PathParam("attribute") String attribute,
            @Context HttpHeaders hh,
            @Context UriInfo ui,
            MultipartFormDataInput input) {
		Response response = null;
		InputStream inStream = null;
        ByteArrayOutputStream baos = null;
		try {
			E entity = this.getSearch().findById(id).getUniqueResult();
			if (entity != null) {
				Field fieldAttribute = ReflectionUtils.getField(attribute, entityClass);
				if (fieldAttribute != null) {
					Method getterAttribute = ReflectionUtils.getFieldGetter(entityClass, fieldAttribute.getType(), attribute);
					if (getterAttribute != null && getterAttribute.isAnnotationPresent(Lob.class)) {
						Method setterAttribute = ReflectionUtils.getFieldSetter(entityClass, attribute);
						Method setterContentType = null;
						Method setterFileName = null;
						Method setterSize = null;
						if (attribute.endsWith(ATTR_SUFIX_BINARY)) {
							String attr = attribute.substring(0, attribute.indexOf(ATTR_SUFIX_BINARY));
							setterContentType = ReflectionUtils.getFieldSetter(entityClass, attr + ATTR_SUFIX_CONTENTTYPE);
							setterFileName = ReflectionUtils.getFieldSetter(entityClass, attr + ATTR_SUFIX_FILENAME);
							setterSize = ReflectionUtils.getFieldSetter(entityClass, attr + ATTR_SUFIX_SIZE);
						}
						boolean isBinary = byte[].class.equals(fieldAttribute.getType());
						List<InputPart> parts = input.getFormDataMap().get(attribute);
			            for (InputPart inPart : parts) {
			            	MultivaluedMap<String, String> pHeaders = inPart.getHeaders();
							String fileName = parseFileName(pHeaders);
							String contentType = parseContentType(pHeaders);
							inStream = inPart.getBody(InputStream.class, null);
							baos = new ByteArrayOutputStream();
				            byte[] buf = new byte[8192];
				            int read = -1;
				            long length = 0;
				            while ((read = inStream.read(buf)) > -1) {
				            	baos.write(buf, 0, read);
				            	if (read > 0) {
				            		length += read;
				            	}
				            }
				            baos.flush();
				            Object data = null;
				            if (isBinary) {
				            	data = baos.toByteArray();
				            } else {
				            	data = baos.toString();
				            }
				            //byte[] binaryData = baos.toByteArray();
				            
				            Long size = Long.valueOf(length);
				            
				            setterAttribute.invoke(entity, data);
				            
				            if (setterContentType != null) {
				            	setterContentType.invoke(entity, contentType);
				            }
				            if (setterFileName != null) {
				            	setterFileName.invoke(entity, fileName);
				            }
				            if (setterSize != null) {
				            	setterSize.invoke(entity, size);
				            }
				            
				            this.getManager().update(entity);
				            
				            response = Response.ok().build();
				            
				            break;
						}
			            
			            if (response == null) {
			            	response = Response.status(Status.BAD_REQUEST).build();
			            }
			            
			            return response;
					} else {
						response = Response.status(Status.BAD_REQUEST).entity("No field accessor or @Lob not present.").build();
					}
				} else {
					response = Response.status(Status.BAD_REQUEST).entity("Field not found.").build();
				}
			} else {
				response = Response.status(Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
			logger.error(String.format("Erro ao realizar upload no atributo: %s", attribute), e);
			response = Response.serverError().build();
		} finally {
			IOUtils.closeQuietly(inStream);
			IOUtils.closeQuietly(baos);
		}

		return response;

	}
	
	/**
	 * Recupera o 'filename' contido no header 'Content-Disposition'
	 * 
	 * @param headers
	 * @return
	 */
	private String parseFileName(MultivaluedMap<String, String> headers) {
		String[] contentDispositionHeader = headers.getFirst(HEADER_CONTENT_DISPOSITION).split(";");
		for (String name : contentDispositionHeader) {
			if ((name.trim().startsWith("filename"))) {
				String[] tmp = name.split("=");
				String fileName = tmp[1].trim().replaceAll("\"", "");
				return fileName;
			}
		}
		return RandomStringUtils.randomAlphanumeric(10);
	}
	
	/**
	 * Recupera o valor do header 'Content-Type'
	 * 
	 * @param headers
	 * @return
	 */
	private String parseContentType(MultivaluedMap<String, String> headers) {
		return headers.getFirst(HEADER_CONTENT_TYPE);
	}

}
