package br.com.framework.view.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import br.com.framework.domain.api.BaseEntity;
import br.com.framework.model.exception.ModelException;
import br.com.framework.model.manager.api.BaseManager;
import br.com.framework.search.api.Search;
import br.com.framework.view.datamodel.LazyEntityDataModel;

/**
 * Classe basica de controlador.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <PK> Classe da chave primária 
 * @param <E> Classe da entidade
 * @param <B> Interface de pesquisa
 * @param <Negocio> Classe do Manager
 */
public abstract class BaseCRUDController<PK extends Serializable, E extends BaseEntity<PK>, B extends Search<PK, E>, 
	Negocio extends BaseManager<PK, E, B>> extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String OUTCOME_LIST = "list";
	public static final String OUTCOME_EDIT = "edit";
	public static final String OUTCOME_VIEW = "view";
	public static final String OUTCOME_LOG = "log";
	
	public static final String BASE_CRUD_PATH = "/protected/cadastros/";
	public static final String BASE_LOG_PATH = "/protected/log/";

	@Inject
	protected Conversation conversation;
	
	protected Negocio negocio;
	protected B searchable;
	
	protected E entity;
	protected Class<E> entityClass;
	
	protected List<E> entityList = new ArrayList<E>();
	
	private LazyEntityDataModel<E> lazyDataModel;

	private int pageIndex = 0;
	private int registersPerPage = 10;

	private boolean isOnView;

	/**
	 * @param entityClass
	 */
	public BaseCRUDController(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public BaseCRUDController() {
		super();
	}

	/**
	 * @return
	 */
	protected Negocio getNegocio() {
		return negocio;
	}

	/**
	 * Deve ser sobrescrito na classe concreta para injeção do componente.
	 * 
	 * @param negocio
	 */
	protected void setNegocio(Negocio manager) {
		this.negocio = manager;
	}

	/**
	 * @return the entity
	 */
	public E getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(E entity) {
		this.entity = entity;
	}

	/**
	 * @return the entityList
	 */
	public List<E> getEntityList() {
		return entityList;
	}

	/**
	 * @param entityList
	 *            the entityList to set
	 */
	public void setEntityList(List<E> entityList) {
		this.entityList = entityList;
	}

	/**
	 * @param isOnView
	 *            the isOnView to set
	 */
	public void setOnView(boolean isOnView) {
		this.isOnView = isOnView;
	}

	/**
	 * @return the isOnView
	 */
	public boolean isOnView() {
		return isOnView;
	}

	public LazyEntityDataModel<E> getLazyDataModel() {
		return lazyDataModel;
	}

	public void setLazyDataModel(LazyEntityDataModel<E> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

	/**
	 * Inicia uma conversação
	 */
	protected void initConversation() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	/**
	 * Finaliza uma conversação
	 */
	protected void endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
		}
	}

	/**
	 * Inicializa a entity.
	 */
	protected void initNewEntity() {
		try {
			entity = getEntityClass().newInstance();
		} catch (Exception e) {
			logger.error("Error inesperado ao instanciar nova entity: "
							+ getEntityClassSimpleName(), e);
			getFacesUtil().addMessageError(null, getMessageResource().get("erro.inesperado.instanciar", getEntityClassSimpleName()));
		}
	}

	/**
	 * Retorna o outcome (navegação) para página de listagem.
	 * 
	 * @return
	 */
	protected String getOutcomeList() {
		return BASE_CRUD_PATH + String.format("%s/%s", getEntityClassSimpleName(),
						OUTCOME_LIST + getEntityClassSimpleName()) + FACES_REDIRECT;
	}

	/**
	 * Retorna o outcome (navegação) para página de edição.
	 * 
	 * @return
	 */
	protected String getOutcomeEdit() {
		return BASE_CRUD_PATH + String.format("%s/%s", getEntityClassSimpleName(),
						OUTCOME_EDIT + getEntityClassSimpleName()) + FACES_REDIRECT;
	}

	/**
	 * Retorna o outcome (navegação) para página de visualização.
	 * 
	 * @return
	 */
	protected String getOutcomeView() {
		return BASE_CRUD_PATH + String.format("%s/%s", getEntityClassSimpleName(),
						OUTCOME_VIEW + getEntityClassSimpleName()) + FACES_REDIRECT;
	}
	
	/**
	 * Retorna o outcome (navegação) para página de log de revisões.
	 * 
	 * @return
	 */
	protected String getOutcomeLog() {
		/*return BASE_CRUD_PATH + String.format("%s/%s", getEntityClassSimpleName(),
						OUTCOME_LOG + getEntityClassSimpleName()) + FACES_REDIRECT;*/
		return BASE_LOG_PATH + OUTCOME_LOG + FACES_REDIRECT;
	}
	
	/**
	 * @return
	 */
	protected Class<E> getEntityClass() {
		return entityClass;
	}

	/**
	 * O nome da classe de entity.
	 * 
	 * @return
	 */
	protected String getEntityClassSimpleName() {
		return getEntityClass().getSimpleName();
	}

	/**
	 * Deve sobrescrito caso haja necessidade de realizar operações antes de
	 * renderizar a tela de listagem.
	 */
	protected void preList() {
	}

	/**
	 * Deve sobrescrito caso haja necessidade de realizar operações antes de
	 * renderizar a tela de criação.
	 */
	protected void beforeCreate() {
	}
	
	/**
	 * Deve sobrescrito caso haja necessidade de realizar operações antes de
	 * renderizar a tela de edição.
	 */
	protected void beforeEdit() {
	}

	/**
	 * Deve sobrescrito caso haja necessidade de realizar operações antes de
	 * renderizar a tela de visualização.
	 */
	protected void beforeView() {
	}
	
	/**
	 * Navega para tela de listagem da entity.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String list() {
		initConversation();
		preList();
		lazyDataModel = new LazyEntityDataModel<E>(getSearchable());
		entityList = (List<E>) lazyDataModel.getWrappedData();
		return getOutcomeList();
	}

	/**
	 * Inicia o fluxo de criação de uma entity.
	 * 
	 * @return
	 */
	public String create() {
		initConversation();
		initNewEntity();
		beforeCreate();
		return getOutcomeEdit();
	}

	/**
	 * Salva a entity.
	 * 
	 * @return
	 */
	public String save() {
		try {
			if (getEntity().getId() == null) {
				this.entity = getNegocio().insert(getEntity());
			} else {
				this.entity = getNegocio().update(getEntity());
			}
			endConversation();
			return list();
		} catch (ModelException e) {
			logger.error("Error de negocio ao salvar entity: "
					+ getEntityClassSimpleName(), e);
			addMessagesFromException(e);
		} catch (ConstraintViolationException e) {
			logger.error(
					"Error de violação de constraints ao salvar entity: "
							+ getEntityClassSimpleName(), e);
			addMessagesFromException(e);
		} catch (Exception e) {
			logger.error( "Error inesperado ao salvar entity: "
					+ getEntityClassSimpleName(), e);
			addMessagesFromException(e);
		}
		return null;
	}

	/**
	 * Inicia o fluxo de edição de uma entity.
	 * 
	 * @return
	 */
	public String edit() {
		if (getEntity() != null) {
			entity = getNegocio().refresh(getEntity());
			beforeEdit();
			return getOutcomeEdit();
		} else {
			getFacesUtil().addMessageWarn(null, "erro.geral.nenhumRegistroSelecionado");
		}
		return null;
	}


	/**
	 * Remove uma entity.
	 * 
	 * @return
	 */
	public String remove() {
		if (getEntity() != null) {
			try {
				getNegocio().remove(getEntity());
				return list();
			} catch (ModelException e) {
				logger.error( "Error de negocio ao remover entity: "
						+ getEntityClassSimpleName(), e);
				addMessagesFromException(e);
			} catch (ConstraintViolationException e) {
				logger.error("Error de violação de constraints ao remover entity: "
								+ getEntityClassSimpleName(), e);
				addMessagesFromException(e);
			} catch (Exception e) {
				logger.error("Error inesperado ao remover entity: "
						+ getEntityClassSimpleName(), e);
				addMessagesFromException(e);
			}
		} else {
			getFacesUtil().addMessageWarn(null, "erro.geral.nenhumRegistroSelecionado");
		}
		return null;
	}
	
	protected boolean initialized = false;
	
	/* (non-Javadoc)
	 * @see br.com.framework.view.controller.BaseController#init()
	 */
	@Override
	public void init() {
		super.init();
		boolean isLoginRedirect = getFacesContext().getExternalContext().getRequestParameterMap().containsKey("login-redirect");
		if (isLoginRedirect) {
			list();
		}
	}
	
	public String cancel() {
		initNewEntity();
		return list() + FACES_REDIRECT;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getRegistersPerPage() {
		return registersPerPage;
	}

	public void setRegistersPerPage(int registrosPorPagina) {
		this.registersPerPage = registrosPorPagina;
	}

	/**
	 * @return
	 */
	protected B getSearchable() {
		return searchable;
	}

	/**
	 * Deve ser sobrescrito na classe concreta para injeção do componente.
	 * 
	 * @param search
	 */
	protected void setSearchable(B searchable) {
		this.searchable = searchable;
	}

}
