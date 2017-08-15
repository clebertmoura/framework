/**
 * 
 */
package br.com.framework.view.util;

import java.util.Iterator;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.util.resource.MessageResource;
import br.com.framework.util.resource.MessageResourceFactory;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class FacesUtil {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FacesUtil.class);

	public static final String DEFAULT_RESOURCE_BUNDLE = "Mensagens";
	
	private MessageResource messageResource;
	
	/**
	 * @param bundleName
	 * @param locale
	 */
	public FacesUtil(String bundleName, Locale locale, ClassLoader classLoader) {
		this.messageResource = MessageResourceFactory.createMessageResource(bundleName, locale, classLoader);
	}
	
	/**
	 * Retorna a instancia do {@link MessageResource}.
	 * 
	 * @return
	 */
	public MessageResource getMessageResource() {
		return messageResource;
	}
	
	/**
	 * Adiciona uma mensagem ao {@link FacesContext}
	 * 
	 * @param componentId 
	 * 	Id do componente ao qual a mensagem será associada.
	 * @param severity
	 * 	Severidade da mensagem do tipo {@link Severity}. Ex: FacesMessage.SEVERITY_INFO, FacesMessage.SEVERITY_WARN, etc.
	 * @param msgSummary
	 * 	Mensagem.
	 * @param msgSummaryParams
	 * 	Parâmetros para a mensagem.
	 * @param msgDetail
	 * 	Mensagem de detalhamento.
	 * @param msgDetailParams
	 * 	Parâmetros para a Mensagem de detalhamento.
	 */
	public void addMessage(String componentId, Severity severity,
			String msgSummary, Object[] msgSummaryParams, String msgDetail, Object[] msgDetailParams) {
		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(severity);
		if (msgSummary != null) {
			String msg = null;
			if (msgSummaryParams != null) {
				msg = getMessageResource().get(msgSummary, msgSummaryParams);
			} else {
				msg = getMessageResource().get(msgSummary);
			}
			facesMessage.setSummary(msg);
		}
		if (msgDetail != null) {
			String msg = null;
			if (msgDetailParams != null) {
				msg = getMessageResource().get(msgDetail, msgDetailParams);
			} else {
				msg = getMessageResource().get(msgDetail);
			}
			facesMessage.setDetail(msg);
		}
		String clientId = null;
		if (componentId != null) {
			UIComponent component = findComponent(getFacesContext()
					.getViewRoot(), componentId);
			if (component != null) {
				clientId = component.getClientId(getFacesContext());
			} else {
				LOGGER.warn(String.format("Nao foi encontrado componente com o id %s na view.", componentId));
			}
		}
		getFacesContext().addMessage(clientId, facesMessage);
	}
	
	
	/**
	 * Adiciona uma mensagem ao {@link FacesContext}
	 * 
	 * @param componentId 
	 * 	Id do componente ao qual a mensagem será associada.
	 * @param severity
	 * 	Severidade da mensagem do tipo {@link Severity}. Ex: FacesMessage.SEVERITY_INFO, FacesMessage.SEVERITY_WARN, etc.
	 * @param msgSummary
	 * 	Mensagem a ser utilizada.
	 * @param msgDetail
	 * 	Detalhamento a ser utilizado
	 */
	public void addMessage(String componentId, Severity severity,
			String msgSummary, String msgDetail) {
		addMessage(componentId, severity, msgSummary, null, msgDetail, null);
	}
	
	/**
	 * Adiciona uma mensagem ao {@link FacesContext}
	 * 
	 * @param componentId 
	 * 	Id do componente ao qual a mensagem será associada.
	 * @param severity
	 * 	Severidade da mensagem do tipo {@link Severity}. Ex: FacesMessage.SEVERITY_INFO, FacesMessage.SEVERITY_WARN, etc.
	 * @param msgSummary
	 * 	Mensagem a ser utilizada.
	 */
	public void addMessage(String componentId, Severity severity, String msgSummary) {
		addMessage(componentId, severity, msgSummary, null);
	}
	
	/**
	 * Adiciona uma mensagem de severidade INFO ao {@link FacesContext}
	 * 
	 * @param componentId 
	 * 	Id do componente ao qual a mensagem será associada.
	 * @param msgSummary
	 * 	Mensagem a ser utilizada.
	 * @param msgDetail
	 * 	Detalhamento a ser utilizado
	 */
	public void addMessageInfo(String componentId, String msgSummary, String msgDetail) {
		addMessage(componentId, FacesMessage.SEVERITY_INFO, msgSummary, msgDetail);
	}
	
	/**
	 * Adiciona uma mensagem de severidade INFO ao {@link FacesContext}
	 * 
	 * @param componentId 
	 * 	Id do componente ao qual a mensagem será associada.
	 * @param msgSummary
	 * 	Mensagem a ser utilizada.
	 */
	public void addMessageInfo(String componentId, String msgSummary) {
		addMessageInfo(componentId, msgSummary, null);
	}
	
	/**
	 * Adiciona uma mensagem de severidade ERROR ao {@link FacesContext}
	 * 
	 * @param componentId 
	 * 	Id do componente ao qual a mensagem será associada.
	 * @param msgSummary
	 * 	Mensagem a ser utilizada.
	 * @param msgDetail
	 * 	Detalhamento a ser utilizado
	 */
	public void addMessageError(String componentId, String msgSummary, String msgDetail) {
		addMessage(componentId, FacesMessage.SEVERITY_ERROR, msgSummary, msgDetail);
	}
	
	/**
	 * Adiciona uma mensagem de severidade ERROR ao {@link FacesContext}
	 * 
	 * @param componentId 
	 * 	Id do componente ao qual a mensagem será associada.
	 * @param msgSummary
	 * 	Mensagem a ser utilizada.
	 */
	public void addMessageError(String componentId, String msgSummary) {
		addMessageError(componentId, msgSummary, null);
	}
	
	/**
	 * Adiciona uma mensagem de severidade WARN ao {@link FacesContext}
	 * 
	 * @param componentId 
	 * 	Id do componente ao qual a mensagem será associada.
	 * @param msgSummary
	 * 	Mensagem a ser utilizada.
	 * @param msgDetail
	 * 	Detalhamento a ser utilizado
	 */
	public void addMessageWarn(String componentId, String msgSummary, String msgDetail) {
		addMessage(componentId, FacesMessage.SEVERITY_WARN, msgSummary, msgDetail);
	}
	
	/**
	 * Adiciona uma mensagem de severidade WARN ao {@link FacesContext}
	 * 
	 * @param componentId 
	 * 	Id do componente ao qual a mensagem será associada.
	 * @param msgSummary
	 * 	Mensagem a ser utilizada.
	 */
	public void addMessageWarn(String componentId, String msgSummary) {
		addMessageWarn(componentId, msgSummary, null);
	}
	
	/**
	 * Adiciona uma mensagem de severidade FATAL ao {@link FacesContext}
	 * 
	 * @param componentId 
	 * 	Id do componente ao qual a mensagem será associada.
	 * @param msgSummary
	 * 	Mensagem a ser utilizada.
	 * @param msgDetail
	 * 	Detalhamento a ser utilizado
	 */
	public void addMessageFatal(String componentId, String msgSummary, String msgDetail) {
		addMessage(componentId, FacesMessage.SEVERITY_FATAL, msgSummary, msgDetail);
	}
	
	/**
	 * Adiciona uma mensagem de severidade FATAL ao {@link FacesContext}
	 * 
	 * @param componentId 
	 * 	Id do componente ao qual a mensagem será associada.
	 * @param msgSummary
	 * 	Mensagem a ser utilizada.
	 */
	public void addMessageFatal(String componentId, String msgSummary) {
		addMessageFatal(componentId, msgSummary, null);
	}
	
	/**
	 * Remove as mensagens contidas no {@link FacesContext}
	 */
	public void removeMessages() {
		getFacesContext().getMessageList().clear();
	}
	
	/**
	 * Recupera um componente a partir de um id e o seu componente pai.
	 * 
	 * @param parent
	 *            O componente pai.
	 * @param componentId
	 *            O id do componente a ser procurado.
	 * @return O componente encontrado.
	 */
	public UIComponent findComponent(UIComponent parent, String componentId) {
		UIComponent component = null;

		if (!componentId.equals(parent.getId())) {
			Iterator<UIComponent> children = parent.getFacetsAndChildren();
			for (Iterator<UIComponent> iterator = children; iterator.hasNext()
					&& (component == null);) {
				UIComponent child = iterator.next();
				component = findComponent(child, componentId);
			}
		} else {
			component = parent;
		}
		return component;
	}
	
	/**
	 * Recupera a instância do {@link FacesContext}.
	 * 
	 * @return
	 */
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * Recupera a instância do {@link ExternalContext}.
	 * 
	 * @return
	 */
	public ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}

}
