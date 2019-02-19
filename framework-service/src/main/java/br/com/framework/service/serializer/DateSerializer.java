package br.com.framework.service.serializer;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Classe responsável por converter uma {@link Date} para {@link String}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class DateSerializer extends StdSerializer<Date> {

	private static final long serialVersionUID = 1L;
	
	public DateSerializer() {
		super(Date.class);
	}

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider sp)
			throws IOException {
		gen.writeString(DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(value));
	}
}