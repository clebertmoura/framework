package br.com.framework.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 * 
 */
@FacesConverter(value = "pickListObjectConverter")
public class PickListObjectConverter implements Converter {
	
	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		Object ret = null;
		if (component instanceof PickList) {
			Object dualList = ((PickList) component).getValue();
			DualListModel<?> dl = (DualListModel<?>) dualList;
			for (Object o : dl.getSource()) {
				String id = "" + o.hashCode();
				if (value.equals(id)) {
					ret = o;
					break;
				}
			}
			if (ret == null)
				for (Object o : dl.getTarget()) {
					String id = "" + o.hashCode();
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
	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
		String str = "";
		if (object != null)
			str += object.hashCode();
		return str;
	}
}
