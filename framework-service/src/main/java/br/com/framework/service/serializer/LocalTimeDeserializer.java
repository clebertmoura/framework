package br.com.framework.service.serializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Classe responsável por converter uma {@link String} para {@link LocalTime}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class LocalTimeDeserializer extends StdDeserializer<LocalTime> {

	private static final long serialVersionUID = 1L;
	
	public LocalTimeDeserializer() {
		super(LocalTime.class);
	}

	@Override
	public LocalTime deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException{
		return LocalTime.parse(jp.readValueAs(String.class), DateTimeFormatter.ISO_LOCAL_TIME);
	}

}