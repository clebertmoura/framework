#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.converter;

import java.sql.Time;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * {@link AttributeConverter} para campos do tipo {@link LocalTime}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Converter(autoApply = true)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, Time> {
	
    @Override
    public Time convertToDatabaseColumn(LocalTime locTime) {
    	return (locTime == null ? null : Time.valueOf(locTime));
    }

    @Override
    public LocalTime convertToEntityAttribute(Time sqlTime) {
    	return (sqlTime == null ? null : sqlTime.toLocalTime());
    }
    
}