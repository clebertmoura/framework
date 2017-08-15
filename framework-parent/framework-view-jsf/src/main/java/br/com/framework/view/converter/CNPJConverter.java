package br.com.framework.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.caelum.stella.format.CNPJFormatter;

@FacesConverter(value = "CNPJConverter", forClass = String.class)
public class CNPJConverter implements Converter {

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
		CNPJFormatter formatter = new CNPJFormatter();
		if (value != null && !value.isEmpty()){
			return formatter.unformat(value);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {
		CNPJFormatter formatter = new CNPJFormatter();
		if (value != null && !value.toString().trim().isEmpty()) {
			return formatter.format(value.toString().trim());
		}
		return "";
	}
}