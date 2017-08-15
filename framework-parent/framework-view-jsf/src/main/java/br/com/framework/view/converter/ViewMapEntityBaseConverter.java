package br.com.framework.view.converter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.framework.domain.api.BaseEntity;

@FacesConverter("entityConverter")
public class ViewMapEntityBaseConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String VIEW_KEY_PREFIX = "br.com.framework.view.converter.ViewMapEntityBaseConverter";
	private static final String empty = "";

	/**
	 * @param context
	 * @return
	 */
	private Map<String, Object> getViewMap(FacesContext context, String componentId) {
		Map<String, Object> viewMap = context.getViewRoot().getViewMap();
		String viewKey = VIEW_KEY_PREFIX + componentId;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Object> idMap = (Map) viewMap.get(viewKey);
		if (idMap == null) {
			idMap = new HashMap<String, Object>();
			viewMap.put(viewKey, idMap);
		}
		return idMap;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent c, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		return getViewMap(context, c.getId()).get(value);
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String getAsString(FacesContext context, UIComponent c, Object value) {
		if (value == null || ((BaseEntity) value).getId() == null) {
			return empty;
		}
		String id = ((BaseEntity) value).getId().toString();
		getViewMap(context, c.getId()).put(id, value);
		return id;
	}
}