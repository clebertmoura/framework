package br.com.framework.service.serializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Classe responsável por converter uma {@link LocalDateTime} para {@link String}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

	private static final long serialVersionUID = 1L;
	
	public static final String LOCAL_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	
	private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LocalDateTimeSerializer.LOCAL_DATETIME_PATTERN);

	public LocalDateTimeSerializer() {
		super(LocalDateTime.class);
	}

	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider sp)
			throws IOException{
		gen.writeString(value.format(formatter));
	}
}