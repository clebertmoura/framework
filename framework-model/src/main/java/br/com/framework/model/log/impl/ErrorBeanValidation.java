package br.com.framework.model.log.impl;

import java.util.List;

import javax.validation.Path;

import com.google.gson.Gson;

public class ErrorBeanValidation extends ErrorDefault {

	private static final long serialVersionUID = -4274481014142169199L;

	private Object rootBean;
	private Object invalidValue;
	private Path propertyPath;
	private Integer businessRuleCode;

	public ErrorBeanValidation(Object rootBean, Object invalidValue, Path propertyPath, Integer businessRuleCode, Integer httpStatus,
			EnumLayerImpl layer, Throwable cause, String message, List<ErrorDefault> erros) {
		super(httpStatus, layer, cause, message, erros);
		this.rootBean = rootBean;
		this.invalidValue = invalidValue;
		this.propertyPath = propertyPath;
		this.businessRuleCode = businessRuleCode;
	}

	public ErrorBeanValidation(Object rootBean, Object invalidValue, Path propertyPath, Integer businessRuleCode,
			Throwable cause, String message) {
		super(cause, message);
		this.rootBean = rootBean;
		this.invalidValue = invalidValue;
		this.propertyPath = propertyPath;
		this.businessRuleCode = businessRuleCode;
	}

	public ErrorBeanValidation(Object rootBean, Object invalidValue, Path propertyPath, Integer businessRuleCode,
			Throwable cause, String message, List<ErrorDefault> erros) {
		super(cause, message, erros);
		this.rootBean = rootBean;
		this.invalidValue = invalidValue;
		this.propertyPath = propertyPath;
		this.businessRuleCode = businessRuleCode;
	}

	public Object getRootBean() {
		return rootBean;
	}

	public void setRootBean(Object rootBean) {
		this.rootBean = rootBean;
	}

	public Object getInvalidValue() {
		return invalidValue;
	}

	public void setInvalidValue(Object invalidValue) {
		this.invalidValue = invalidValue;
	}

	public Path getPropertyPath() {
		return propertyPath;
	}

	public void setPropertyPath(Path propertyPath) {
		this.propertyPath = propertyPath;
	}
	
//	public String toString() {
//		
//		StringBuilder log = new StringBuilder();
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//		
//		log.append(sdf.format(timestamp));
//		log.append("httpStatus: " + httpStatus+"\n");
//		if(cause != null)
//			log.append("cause: " + cause.getMessage()+"\n");
//		log.append("message:" + message+"\n");
//		log.append("rootBean: " + rootBean.toString()+"\n");
//		log.append("invalidValue: " + invalidValue.toString()+"\n");
//		log.append("propertyPath: " + propertyPath.toString()+"\n");
//		log.append("businessRuleCode: " + businessRuleCode+"\n");
//		
//		for(ErrorDefault erro: erros) {
//			log.append(sdf.format(erro.timestamp)+"\n");
//			log.append("httpStatus: " + erro.httpStatus+"\n");
//			if(erro.cause != null)
//				log.append("cause: " + erro.cause.getMessage()+"\n");
//			log.append("message:" + erro.message+"\n");
//		}
//		
//		return log.toString();
//	}
	
	public String toString() {
		
		return new Gson().toJson(this);
	}

}
