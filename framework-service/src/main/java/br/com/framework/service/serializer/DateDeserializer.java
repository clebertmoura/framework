package br.com.framework.service.serializer;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Classe responsável por converter uma {@link String} para {@link Date}.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class DateDeserializer extends StdDeserializer<Date> {

	private static final long serialVersionUID = 1L;
	
	private static final FastDateFormat[] DATE_FORMATS = {
			DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT, 
			DateFormatUtils.ISO_DATETIME_FORMAT};
	
	public DateDeserializer() {
		super(Date.class);
	}

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException{
		Date date = null;
		String readValueAs = jp.readValueAs(String.class);
		if (readValueAs != null) {
			for (FastDateFormat dateFormat : DATE_FORMATS) {
				try {
					date = dateFormat.parse(jp.readValueAs(String.class));
					break;
				} catch (ParseException e) {
				}
			}
		}
		return date;
	}

}