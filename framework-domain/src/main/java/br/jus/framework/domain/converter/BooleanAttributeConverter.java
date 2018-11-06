package br.jus.framework.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * {@link AttributeConverter} para campos do tipo {@link Boolean}
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
@Converter(autoApply = true)
public class BooleanAttributeConverter implements AttributeConverter<Boolean, String> {
	
    @Override
    public String convertToDatabaseColumn(Boolean boolValue) {
    	return (boolValue == null ? null : (Boolean.TRUE.equals(boolValue) ? Boolean.TRUE.toString() : Boolean.FALSE.toString()));
    }

    @Override
    public Boolean convertToEntityAttribute(String sqlStatus) {
    	return (sqlStatus == null ? null : (sqlStatus.equals(Boolean.TRUE.toString()) ? Boolean.TRUE : Boolean.FALSE));
    }
    
}