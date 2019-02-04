package br.com.framework.model.log.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

public class ErrorDefault implements Serializable {

	private static final long serialVersionUID = -3387250237117975860L;

	protected Integer httpStatus;
	protected EnumLayerImpl layer;
	protected Throwable cause;
	protected String message;
	protected Date timestamp;
	protected List<ErrorDefault> erros = new ArrayList<>();

	public ErrorDefault(Integer httpStatus, EnumLayerImpl layer, Throwable cause, String message,
			List<ErrorDefault> erros) {
		super();
		this.httpStatus = httpStatus;
		this.layer = layer;
		this.cause = cause;
		this.message = message;
		this.erros = erros;
		this.timestamp = new Date();
	}

	public ErrorDefault(Throwable cause, String message) {
		super();
		this.cause = cause;
		this.message = message;
		this.timestamp = new Date();
		this.layer = EnumLayerImpl.GLOBAL;
	}

	public ErrorDefault(Throwable cause, String message, List<ErrorDefault> erros) {
		super();
		this.cause = cause;
		this.message = message;
		this.erros = erros;
		this.timestamp = new Date();
		this.layer = EnumLayerImpl.GLOBAL;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public EnumLayerImpl getLayer() {
		return layer;
	}

	public Throwable getCause() {
		return cause;
	}

	public String getMessage() {
		return message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public List<ErrorDefault> getErrors() {
		return erros;
	}

//	public String toString() {
//
//		StringBuilder log = new StringBuilder();
//
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//
//		log.append(sdf.format(timestamp) + "\n");
//		log.append("httpStatus: " + httpStatus + "\n");
//		if (cause != null)
//			log.append("cause: " + cause.getMessage() + "\n");
//		log.append("message:" + message + "\n");
//
//		for (ErrorDefault erro : erros) {
//			log.append(sdf.format(erro.timestamp));
//			log.append("httpStatus: " + erro.httpStatus + "\n");
//			if (erro.cause != null)
//				log.append("cause: " + erro.cause.getMessage() + "\n");
//			log.append("message:" + erro.message + "\n");
//		}
//
//		return log.toString();
//	}

	public String toString() {
		
		return new Gson().toJson(this);
	}
}
