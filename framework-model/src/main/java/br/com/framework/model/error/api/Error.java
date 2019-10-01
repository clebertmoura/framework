package br.com.framework.model.error.api;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public interface Error extends Serializable {

	Integer getHttpStatus();

	Error httpStatus(Integer httpStatus);

	ErrorLayer getLayer();

	Error layer(ErrorLayer layer);

	String getMessage();

	Error message(String message);

	OffsetDateTime getTimestamp();

	Error timestamp(OffsetDateTime timestamp);

	List<Error> getErrors();

	Error addError(Error error);

	Error addErrors(List<Error> errors);

}