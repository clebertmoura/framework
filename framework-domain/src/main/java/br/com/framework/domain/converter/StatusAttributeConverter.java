package br.com.framework.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.framework.domain.enums.Status;

/**
 * {@link AttributeConverter} para campos do tipo {@link Status}
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Converter(autoApply = true)
public class StatusAttributeConverter implements AttributeConverter<Status, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(Status status) {
    	return (status == null ? null : (Status.ACTIVE.equals(status) ? 1 : 0));
    }

    @Override
    public Status convertToEntityAttribute(Integer sqlStatus) {
    	return (sqlStatus == null ? null : (sqlStatus == 1 ? Status.ACTIVE : Status.INACTIVE));
    }
    
}