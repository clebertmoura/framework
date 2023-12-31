package br.com.framework.service.serializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Classe responsável por converter uma {@link LocalTime} para {@link String}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class LocalTimeSerializer extends StdSerializer<LocalTime> {

	private static final long serialVersionUID = 1L;
	
	public LocalTimeSerializer() {
		super(LocalTime.class);
	}

	@Override
	public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider sp)
			throws IOException{
		gen.writeString(value.format(DateTimeFormatter.ISO_LOCAL_TIME));
	}
}