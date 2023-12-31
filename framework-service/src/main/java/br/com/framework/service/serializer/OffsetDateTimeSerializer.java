package br.com.framework.service.serializer;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Classe responsável por converter uma {@link OffsetDateTime} para {@link String}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class OffsetDateTimeSerializer extends StdSerializer<OffsetDateTime> {

	private static final long serialVersionUID = 1L;
	
	public OffsetDateTimeSerializer() {
		super(OffsetDateTime.class);
	}

	@Override
	public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider sp)
			throws IOException{
		gen.writeString(value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
	}
}