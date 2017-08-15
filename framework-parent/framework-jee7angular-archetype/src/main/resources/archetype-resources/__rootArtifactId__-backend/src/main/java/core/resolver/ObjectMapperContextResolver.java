#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.resolver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.framework.service.serializer.LocalDateDeserializer;
import br.com.framework.service.serializer.LocalDateSerializer;
import br.com.framework.service.serializer.LocalDateTimeDeserializer;
import br.com.framework.service.serializer.LocalDateTimeSerializer;
import br.com.framework.service.serializer.LocalTimeDeserializer;
import br.com.framework.service.serializer.LocalTimeSerializer;

/**
 * Classe responsável por registrar no contexto do JAX-RS os serializadores/deserializadores de campos do tipo: LocalDate, LocalTime e LocalDateTime.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    final ObjectMapper mapper = new ObjectMapper();

    public ObjectMapperContextResolver() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        module.addSerializer(LocalTime.class, new LocalTimeSerializer());
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }  
}