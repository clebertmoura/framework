$output.generateIf($enum.config.isCustomType())##
$output.java("${enum.model.packageName}.converter", "${enum.model.type}Converter")##

$output.require("${enum.model.packageName}.enums.${enum.model.type}")##

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ${enum.model.type}Converter implements AttributeConverter<${enum.model.type}, String> {

    public String convertToDatabaseColumn(${enum.model.type} javaEnum) {
        if (javaEnum == null) {
            return null;
        }

        return javaEnum.getValue(); // dbValue...
    }

    public ${enum.model.type} convertToEntityAttribute(String dbValue) {
        if (dbValue == null || dbValue.isEmpty()) {
            return null;
        }

        return ${enum.model.type}.fromValue(dbValue);
    }
}