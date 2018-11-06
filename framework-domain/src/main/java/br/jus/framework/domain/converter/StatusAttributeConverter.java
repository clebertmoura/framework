package br.jus.framework.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.jus.framework.domain.enums.Status;

/**
 * {@link AttributeConverter} para campos do tipo {@link Status}
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
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