package br.com.framework.service.serializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Classe responsável por converter uma {@link String} para {@link LocalDateTime}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class OffsetDateTimeDeserializer extends StdDeserializer<OffsetDateTime> {

	private static final long serialVersionUID = 1L;
	
	public OffsetDateTimeDeserializer() {
		super(OffsetDateTime.class);
	}

	@Override
	public OffsetDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException{
		return OffsetDateTime.parse(jp.readValueAs(String.class), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

}