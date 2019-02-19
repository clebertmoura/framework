package br.com.framework.service.serializer;

import java.io.IOException;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Classe responsável por converter uma {@link String} para {@link OffsetTime}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class OffsetTimeDeserializer extends StdDeserializer<OffsetTime> {

	private static final long serialVersionUID = 1L;
	
	public OffsetTimeDeserializer() {
		super(OffsetTime.class);
	}

	@Override
	public OffsetTime deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException{
		return OffsetTime.parse(jp.readValueAs(String.class), DateTimeFormatter.ISO_OFFSET_TIME);
	}

}