package br.com.framework.model.log.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import com.google.gson.Gson;

public class ErrorBusiness extends ErrorDefault {

	private static final long serialVersionUID = 2111039541398163952L;

	private Integer businessRuleCode;

	public ErrorBusiness(Integer businessRuleCode, Integer httpStatus, EnumLayerImpl layer, Throwable cause,
			String message, List<ErrorDefault> erros) {
		super(httpStatus, layer, cause, message, erros);
		this.businessRuleCode = businessRuleCode;
	}

	public ErrorBusiness(Integer businessRuleCode, Throwable cause, String message) {
		super(cause, message);
		this.businessRuleCode = businessRuleCode;
	}

	public ErrorBusiness(Integer businessRuleCode, Throwable cause, String message, List<ErrorDefault> erros) {
		super(cause, message, erros);
		this.businessRuleCode = businessRuleCode;
	}

	public Integer getBusinessRuleCode() {
		return businessRuleCode;
	}
	
//	public String toString() {
//		
//		StringBuilder log = new StringBuilder();
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//		
//		log.append(sdf.format(timestamp)+"\n");
//		log.append("httpStatus: " + httpStatus+"\n");
//		if(cause != null)
//			log.append("cause: " + cause.getMessage()+"\n");
//		log.append("message:" + message+"\n");
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
