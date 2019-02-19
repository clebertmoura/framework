package br.com.framework.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import br.com.framework.domain.api.BaseEntity;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 * 
 */
@FacesConverter(value = "pickListEntityConverter")
public class PickListEntityConverter implements Converter {
	
	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		Object ret = null;
		if (component instanceof PickList) {
			Object dualList = ((PickList) component).getValue();
			DualListModel<?> dl = (DualListModel<?>) dualList;
			for (Object o : dl.getSource()) {
				String id = "" + ((BaseEntity) o).getId();
				if (value.equals(id)) {
					ret = o;
					break;
				}
			}
			if (ret == null)
				for (Object o : dl.getTarget()) {
					String id = "" + ((BaseEntity) o).getId();
					if (value.equals(id)) {
						ret = o;
						break;
					}
				}
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
		String str = "";
		if (object instanceof BaseEntity) {
			str = "" + ((BaseEntity) object).getId();
		}
		return str;
	}
}
