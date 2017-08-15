package br.com.framework.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "CEPConverter", forClass = String.class)
public class CEPConverter implements Converter {

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
		if(value != null && !value.isEmpty()){
			value = value.replace("-", "");
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {
		if (value != null && !value.toString().trim().isEmpty()) {
			String cep = value.toString().trim();
			return cep.substring(0, 5) + "-" + cep.substring(5);
		}
		return "";
	}
}