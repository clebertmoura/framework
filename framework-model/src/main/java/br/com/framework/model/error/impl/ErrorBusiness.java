package br.com.framework.model.error.impl;

import java.util.List;

import org.apache.http.HttpStatus;

import br.com.framework.model.error.api.Error;
import br.com.framework.model.error.api.ErrorLayer;

/**
 * Representa um Erro que é retornado pelo Backend em uma validação de negócio. 
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
public class ErrorBusiness extends ErrorDefault {

	private static final long serialVersionUID = 2111039541398163952L;

	private Integer businessRuleCode;

	/**
	 * @param businessRuleCode
	 * @param message
	 * @param errors
	 */
	public ErrorBusiness(Integer businessRuleCode, String message, List<Error> errors) {
		super(ErrorLayer.BUSINESS, HttpStatus.SC_BAD_REQUEST, message, errors);
		this.businessRuleCode = businessRuleCode;
	}
	
	/**
	 * @param businessRuleCode
	 * @param message
	 */
	public ErrorBusiness(Integer businessRuleCode, String message) {
		this(businessRuleCode, message, null);
	}

	/**
	 * @param message
	 * @param errors
	 */
	public ErrorBusiness(String message, List<Error> errors) {
		this(null, message, errors);
	}
	
	/**
	 * @param message
	 */
	public ErrorBusiness(String message) {
		super(message);
	}

	public Integer getBusinessRuleCode() {
		return businessRuleCode;
	}
	
	public ErrorBusiness businessRuleCode(Integer businessRuleCode) {
		this.businessRuleCode = businessRuleCode;
		return this;
	}
	
	public Error addErrors(List<Error> errors) {
		this.getErrors().addAll(errors);
		return this;
	}
	
}
