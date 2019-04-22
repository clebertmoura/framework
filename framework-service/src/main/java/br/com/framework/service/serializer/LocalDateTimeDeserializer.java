package br.com.framework.service.serializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private static final Logger LOG = LoggerFactory.getLogger(LocalDateTimeDeserializer.class);
	
	public static final DateTimeFormatter[] DATETIME_FORMATS = {
			DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
	};

	private static final DateTimeFormatter JAVASCRIPT_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static final ZoneId RECIFE_TIMEZONE = ZoneId.of("America/Recife");

    private static final ZoneId UTC_TIMEZONE = ZoneId.of("UTC");

	public LocalDateTimeDeserializer() {
		super(LocalDateTime.class);
	}

	@Override
	public LocalDateTime deserialize(JsonParser json, DeserializationContext ctx) throws IOException, JsonProcessingException {
		LocalDateTime localDateTime = null;
		for (DateTimeFormatter dateTimeFormatter : DATETIME_FORMATS) {
			try {
				localDateTime = LocalDateTime
						.parse(json.getValueAsString(), dateTimeFormatter)
						.atZone(UTC_TIMEZONE)
						.withZoneSameInstant(RECIFE_TIMEZONE)
						.toLocalDateTime();
				break;
			} catch (DateTimeParseException e) {
				LOG.error(String.format("Erro ao deserializar: %s, formato: %s", json.getValueAsString(), dateTimeFormatter), e);
			}
		}
		return localDateTime;
	}


}