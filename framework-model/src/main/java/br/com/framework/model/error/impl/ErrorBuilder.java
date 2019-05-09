package br.com.framework.model.error.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import br.com.framework.model.error.api.Error;
import br.com.framework.model.exception.ModelException;

/**
 * Classe utilitária para criação de Error 
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
public class ErrorBuilder {

	private ErrorBuilder() {
	}
	
	/**
	 * @param message
	 * @return
	 */
	public static ErrorDefault buildError(String message) {
		return new ErrorDefault(message);
	}
	
	
	/**
	 * @param businessRuleCode
	 * @param message
	 * @return
	 */
	public static ErrorBusiness buildErrorBusiness(Integer businessRuleCode, String message) {
		return new ErrorBusiness(businessRuleCode, message);
	}
	
	/**
	 * @param message
	 * @return
	 */
	public static ErrorBusiness buildErrorBusiness(String message) {
		return buildErrorBusiness(null, message);
	}
	
	
	/**
	 * @param me
	 * @return
	 */
	public static List<Error> buildFromModelException(ModelException me) {
		List<Error> errors = new ArrayList<>();
		if (!me.getErrors().isEmpty()) {
			errors.addAll(me.getErrors());
		}
		return errors;
	}
	
	/**
	 * @param rootBean
	 * @param invalidValue
	 * @param propertyPath
	 * @param message
	 * @return
	 */
	public static ErrorBeanValidation buildErrorBeanValidation(String rootBean, Object invalidValue, String propertyPath, 
			String message) {
		return new ErrorBeanValidation(rootBean, invalidValue, propertyPath, message);
	}
	
	/**
	 * @param message
	 * @return
	 */
	public static ErrorBeanValidation buildErrorBeanValidation(String message) {
		return buildErrorBeanValidation(null, null, null, message);
	}
	
	/**
	 * @param cve
	 * @return
	 */
	public static List<ErrorBeanValidation> buildFromConstraintViolationException(ConstraintViolationException cve) {
		List<ErrorBeanValidation> errors = new ArrayList<>();
		if (!cve.getConstraintViolations().isEmpty()) {
			for (ConstraintViolation<?> cv : cve.getConstraintViolations()) {
				String rootBeanClass = cv.getRootBean() != null ? cv.getRootBean().getClass().getSimpleName() : null;
				ErrorBeanValidation cvError = buildErrorBeanValidation(rootBeanClass, cv.getInvalidValue(), 
					cv.getPropertyPath().toString(), cv.getMessage());
				errors.add(cvError);
			}
		}
		return errors;
	}
	
	
}
