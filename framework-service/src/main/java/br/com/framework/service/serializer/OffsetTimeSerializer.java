package br.com.framework.service.serializer;

import java.io.IOException;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Classe responsável por converter uma {@link OffsetTime} para {@link String}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class OffsetTimeSerializer extends StdSerializer<OffsetTime> {

	private static final long serialVersionUID = 1L;
	
	public OffsetTimeSerializer() {
		super(OffsetTime.class);
	}

	@Override
	public void serialize(OffsetTime value, JsonGenerator gen, SerializerProvider sp)
			throws IOException{
		gen.writeString(value.format(DateTimeFormatter.ISO_OFFSET_TIME));
	}
}