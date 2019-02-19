package br.com.framework.service.serializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Classe responsável por converter uma {@link ZonedDateTime} para {@link String}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class ZonedDateTimeSerializer extends StdSerializer<ZonedDateTime> {

	private static final long serialVersionUID = 1L;
	
	public ZonedDateTimeSerializer() {
		super(ZonedDateTime.class);
	}

	@Override
	public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider sp)
			throws IOException{
		gen.writeString(value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
	}
}