package br.com.framework.service.serializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Classe responsável por converter uma {@link String} para {@link ZonedDateTime}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class ZonedDateTimeDeserializer extends StdDeserializer<ZonedDateTime> {

	private static final long serialVersionUID = 1L;
	
	public ZonedDateTimeDeserializer() {
		super(ZonedDateTime.class);
	}

	@Override
	public ZonedDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException{
		return ZonedDateTime.parse(jp.readValueAs(String.class), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

}