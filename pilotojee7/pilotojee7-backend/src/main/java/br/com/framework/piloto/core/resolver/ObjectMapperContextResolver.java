package br.com.framework.piloto.core.resolver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import br.com.framework.service.serializer.DateDeserializer;
import br.com.framework.service.serializer.LocalDateDeserializer;
import br.com.framework.service.serializer.LocalDateSerializer;
import br.com.framework.service.serializer.LocalDateTimeDeserializer;
import br.com.framework.service.serializer.LocalDateTimeSerializer;
import br.com.framework.service.serializer.LocalTimeDeserializer;
import br.com.framework.service.serializer.LocalTimeSerializer;
import br.com.framework.service.serializer.OffsetDateTimeDeserializer;
import br.com.framework.service.serializer.OffsetDateTimeSerializer;
import br.com.framework.service.serializer.OffsetTimeDeserializer;
import br.com.framework.service.serializer.OffsetTimeSerializer;
import br.com.framework.service.serializer.ZonedDateTimeDeserializer;
import br.com.framework.service.serializer.ZonedDateTimeSerializer;

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
        module.addSerializer(Date.class, new DateSerializer());
        module.addDeserializer(Date.class, new DateDeserializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        module.addSerializer(LocalTime.class, new LocalTimeSerializer());
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        module.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer());
        module.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
        module.addSerializer(OffsetTime.class, new OffsetTimeSerializer());
        module.addDeserializer(OffsetTime.class, new OffsetTimeDeserializer());
        module.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer());
        module.addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }  
}