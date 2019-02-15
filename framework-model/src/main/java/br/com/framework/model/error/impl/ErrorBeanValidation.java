package br.com.framework.model.error.impl;

import java.util.List;

import javax.faces.validator.BeanValidator;

import org.apache.http.HttpStatus;

import br.com.framework.model.error.api.Error;
import br.com.framework.model.error.api.ErrorLayer;

/**
 * Representa um Erro que é retornado pelo Backend em uma validação do {@link BeanValidator} 
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
public class ErrorBeanValidation extends ErrorDefault {

	private static final long serialVersionUID = -4274481014142169199L;

	private String rootBean;
	private Object invalidValue;
	private String propertyPath;
	
	/**
	 * @param rootBean
	 * @param invalidValue
	 * @param propertyPath
	 * @param message
	 * @param errors
	 */
	public ErrorBeanValidation(String rootBean, Object invalidValue, String propertyPath, String message, List<Error> errors) {
		super(ErrorLayer.BEAN_VALIDATION, HttpStatus.SC_BAD_REQUEST, message, errors);
		this.rootBean = rootBean;
		this.invalidValue = invalidValue;
		this.propertyPath = propertyPath;
	}
	
	/**
	 * @param rootBean
	 * @param invalidValue
	 * @param propertyPath
	 * @param message
	 */
	public ErrorBeanValidation(String rootBean, Object invalidValue, String propertyPath, String message) {
		this(rootBean, invalidValue, propertyPath, message, null);
	}
	
	/**
	 * @param message
	 * @param erros
	 */
	public ErrorBeanValidation(String message, List<Error> erros) {
		super(message, erros);
	}

	/**
	 * @param message
	 */
	public ErrorBeanValidation(String message) {
		super(message);
	}

	public Object getRootBean() {
		return rootBean;
	}

	public ErrorBeanValidation rootBean(String rootBean) {
		this.rootBean = rootBean;
		return this;
	}

	public Object getInvalidValue() {
		return invalidValue;
	}

	public ErrorBeanValidation invalidValue(Object invalidValue) {
		this.invalidValue = invalidValue;
		return this;
	}

	public String getPropertyPath() {
		return propertyPath;
	}

	public ErrorBeanValidation propertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
		return this;
	}
	
}
