package org.example.server.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import org.example.entity.MedicalCard;
import org.example.server.DependenciesContainer;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class MedicalCardJSONMessageBodyReader implements MessageBodyReader<MedicalCard> {
    private final ObjectMapper mapper;

    public MedicalCardJSONMessageBodyReader() {
        this.mapper = DependenciesContainer.getObjectMapper();
    }

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return type == MedicalCard.class && mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public MedicalCard readFrom(Class<MedicalCard> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        return mapper.readValue(inputStream, MedicalCard.class);
    }
}
