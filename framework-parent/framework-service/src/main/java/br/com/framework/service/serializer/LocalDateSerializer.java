package br.com.framework.service.serializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Classe responsável por converter uma {@link LocalDate} para {@link String}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class LocalDateSerializer extends StdSerializer<LocalDate> {

	private static final long serialVersionUID = 1L;
	
	public static final String LOCAL_DATE_PATTERN = "yyyy-MM-dd";
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LocalDateSerializer.LOCAL_DATE_PATTERN);

	public LocalDateSerializer() {
		super(LocalDate.class);
	}

	@Override
	public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider sp)
			throws IOException, JsonProcessingException {
		gen.writeString(value.format(formatter));
	}
}