package br.com.framework.view.controller;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.model.bean.SessionInfo;
import br.com.framework.model.exception.ModelException;
import br.com.framework.util.resource.MessageResource;
import br.com.framework.view.util.FacesUtil;

/**
 * Classe base para os controladores.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 * @param <E>
 * @param <Negocio>
 */
public class BaseController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FACES_REDIRECT = "?faces-redirect=true";
	public static final String FACES_REDIRECT_NO_CID = "?faces-redirect=true&nocid=true";
	public static final String NO_CID = "?nocid=true";
	public static final String BUNDLE_NAME = "Mensagens";
	
	@Inject @New
	protected SessionInfo userSession;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private transient FacesUtil facesUtil;
	
	protected Locale locale;
	
	public BaseController() {
	}
	
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	/**
	 * Inicializa a entity.
	 */
	@PostConstruct
	public void init() {
		if (userSession != null && userSession.getLocale() == null) {
			userSession.setLocale(getFacesContext().getExternalContext()
					.getRequestLocale());
		}
		getFacesUtil();
	}
	
	/**
	 * Retorna uma instancia do {@link FacesUtil}.
	 * 
	 * @return
	 */
	protected FacesUtil getFacesUtil() {
		if (facesUtil == null) {
			facesUtil = new FacesUtil(BUNDLE_NAME, getLocale(), getClass().getClassLoader());
		}
		return facesUtil;
	}
	
	/**
	 * Retorna o locale da requisição ou o da JVM caso não seja detectado.
	 * 
	 * @return
	 */
	public Locale getLocale() {
		if (locale == null) {
			locale = getFacesContext().getExternalContext().getRequestLocale();
		}
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return locale;
	}
	
	/**
	 * Retorna a instancia do {@link MessageResource}.
	 * 
	 * @return
	 */
	protected MessageResource getMessageResource() {
		return getFacesUtil().getMessageResource();
	}
	
	/**
	 * Adiciona as mensagens {@link FacesMessage} a partir de uma exception {@link ModelException}
	 * @param e
	 */
	protected void addMessagesFromException(ModelException e) {
		if (!e.getMensagensMap().isEmpty()) {
			for (Entry<String, Object[]> entry : e.getMensagensMap()
					.entrySet()) {
				getFacesUtil().addMessageError(
						null,
						getMessageResource().get(entry.getKey(),
								entry.getValue()));
			}
		} else {
			getFacesUtil().addMessageError(null, getMessageResource().get("msg.erro.inesperado.negocio", e.getMessage()));
		}
	}
	
	/**
	 * Adiciona as mensagens {@link FacesMessage} a partir de uma exception {@link ConstraintViolationException}
	 * @param e
	 */
	protected void addMessagesFromException(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> violations = e
				.getConstraintViolations();
		if (!violations.isEmpty()) {
			for (ConstraintViolation<?> violation : violations) {
				getFacesUtil().addMessageError(violation.getPropertyPath().toString(), violation.getMessage());
			}
		}
	}
	
	/**
	 * Adiciona as mensagens {@link FacesMessage} a partir de uma exception {@link Exception}
	 * @param e
	 */
	protected void addMessagesFromException(Exception e) {
		if (e instanceof EJBException) {
			Exception causedByException = ((EJBException) e).getCausedByException();
			if (causedByException instanceof ConstraintViolationException) {
				addMessagesFromException((ConstraintViolationException)causedByException);
			} else if (causedByException instanceof ModelException) {
				addMessagesFromException((ModelException)causedByException);
			} else {
				addMessagesFromException(causedByException);
			}
		} else {
			getFacesUtil().addMessageError(null, getMessageResource().get("msg.erro.inesperado", e.getMessage()));
		}
	}

}
