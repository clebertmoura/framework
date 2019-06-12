package br.com.framework.domain.converter;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * {@link AttributeConverter} para campos do tipo {@link ZonedDateTime}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Converter(autoApply = true)
public class ZonedDateTimeAttributeConverter implements AttributeConverter<ZonedDateTime, Timestamp> {
	
    @Override
    public Timestamp convertToDatabaseColumn(ZonedDateTime locDateTime) {
    	return (locDateTime == null ? null : Timestamp.from(locDateTime.toInstant()));
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
    	return (sqlTimestamp == null ? null : sqlTimestamp.toInstant().atZone(ZoneOffset.UTC));
    }
    
}