package br.com.framework.service.serializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Classe responsável por converter uma {@link String} para {@link LocalDateTime}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

	private static final long serialVersionUID = 1L;

	public static final DateTimeFormatter JAVASCRIPT_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	public static final ZoneId RECIFE_TIMEZONE = ZoneId.of("America/Recife");
	public static final ZoneId UTC_TIMEZONE = ZoneId.of("UTC");

	public LocalDateTimeDeserializer() {
		super(LocalDateTime.class);
	}

	@Override
	public LocalDateTime deserialize(JsonParser json, DeserializationContext ctx) throws IOException, JsonProcessingException {
		return LocalDateTime
					.parse(json.getValueAsString(), JAVASCRIPT_DATETIME_FORMAT)
					.atZone(UTC_TIMEZONE)
					.withZoneSameInstant(RECIFE_TIMEZONE)
					.toLocalDateTime();
	}


}