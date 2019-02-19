package br.com.framework.model.error.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;

import com.google.gson.Gson;

import br.com.framework.model.error.api.Error;
import br.com.framework.model.error.api.ErrorLayer;

/**
 * Representa um Erro que é retornado pelo Backend. 
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
public class ErrorDefault implements Error {

	private static final long serialVersionUID = -3387250237117975860L;

	protected OffsetDateTime timestamp;
	protected ErrorLayer layer;
	
	protected Integer httpStatus;
	protected String message;
	protected List<Error> errors;

	/**
	 * @param layer
	 * @param httpStatus
	 * @param message
	 * @param errors
	 */
	public ErrorDefault(ErrorLayer layer, Integer httpStatus, String message, List<Error> errors) {
		super();
		this.timestamp = OffsetDateTime.now();
		this.layer = layer;
		this.httpStatus = httpStatus;
		this.message = message;
		this.errors = errors != null ? errors : new ArrayList<Error>();
	}
	
	/**
	 * @param layer
	 * @param httpStatus
	 * @param message
	 */
	public ErrorDefault(ErrorLayer layer, Integer httpStatus, String message) {
		this(layer, httpStatus, message, null);
	}
	
	/**
	 * @param httpStatus
	 * @param message
	 * @param errors
	 */
	public ErrorDefault(Integer httpStatus, String message, List<Error> errors) {
		this(ErrorLayer.GLOBAL, httpStatus, message, errors);
	}

	/**
	 * @param message
	 * @param erros
	 */
	public ErrorDefault(String message, List<Error> erros) {
		this(HttpStatus.SC_INTERNAL_SERVER_ERROR, message, erros);
	}
	
	/**
	 * @param message
	 */
	public ErrorDefault(String message) {
		this(message, null);
	}

	/* (non-Javadoc)
	 * @see br.com.framework.model.log.impl.Error#getHttpStatus()
	 */
	@Override
	public Integer getHttpStatus() {
		return httpStatus;
	}
	
	/* (non-Javadoc)
	 * @see br.com.framework.model.log.impl.Error#httpStatus(java.lang.Integer)
	 */
	@Override
	public Error httpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
		return this;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.model.log.impl.Error#getLayer()
	 */
	@Override
	public ErrorLayer getLayer() {
		return layer;
	}
	
	/* (non-Javadoc)
	 * @see br.com.framework.model.log.impl.Error#layer(br.com.framework.model.log.impl.ErrorLayer)
	 */
	@Override
	public Error layer(ErrorLayer layer) {
		this.layer = layer;
		return this;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.model.log.impl.Error#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}
	
	/* (non-Javadoc)
	 * @see br.com.framework.model.log.impl.Error#message(java.lang.String)
	 */
	@Override
	public Error message(String message) {
		this.message = message;
		return this;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.model.log.impl.Error#getTimestamp()
	 */
	@Override
	public OffsetDateTime getTimestamp() {
		return timestamp;
	}
	
	/* (non-Javadoc)
	 * @see br.com.framework.model.log.impl.Error#timestamp(java.time.OffsetDateTime)
	 */
	@Override
	public Error timestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	/* (non-Javadoc)
	 * @see br.com.framework.model.log.impl.Error#getErrors()
	 */
	@Override
	public List<Error> getErrors() {
		if (errors == null) {
			errors = new ArrayList<>();
		}
		return errors;
	}
	
	/* (non-Javadoc)
	 * @see br.com.framework.model.log.impl.Error#addError(br.com.framework.model.log.impl.ErrorDefault)
	 */
	@Override
	public Error addError(Error error) {
		this.getErrors().add(error);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see br.com.framework.model.log.impl.Error#addErrors(java.util.List)
	 */
	@Override
	public Error addErrors(List<Error> errors) {
		this.getErrors().addAll(errors);
		return this;
	}

	public String toString() {
		return new Gson().toJson(this);
	}
}
