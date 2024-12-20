package org.example.server.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import org.example.entity.MedicalCard;
import org.example.server.DependenciesContainer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class MedicalCardListJSONMessageBodyWriter implements MessageBodyWriter<List<MedicalCard>> {
    private final ObjectMapper mapper;

    public MedicalCardListJSONMessageBodyWriter() {
        mapper = DependenciesContainer.getObjectMapper();
    }


    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        if (!mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE)) {
            return false;
        }
        if (List.class.isAssignableFrom(aClass) && type instanceof ParameterizedType) {
            Type actualType = ((ParameterizedType) type).getActualTypeArguments()[0];
            return actualType == MedicalCard.class;
        }

        return false;
    }

    @Override
    public void writeTo(List<MedicalCard> medicalCards, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        mapper.writeValue(outputStream, medicalCards);
    }
}
