package org.example.server.reader;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.example.entity.MedicalCard;
import org.example.entity.ObjectFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class MedicalCardXMLMessageBodyReader implements MessageBodyReader<MedicalCard> {
    private final JAXBContext jaxbContext;

    public MedicalCardXMLMessageBodyReader() {
        try {
            jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return type == MedicalCard.class && mediaType.isCompatible(MediaType.APPLICATION_XML_TYPE);
    }

    @Override
    public MedicalCard readFrom(Class<MedicalCard> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);

            JAXBElement<MedicalCard> root = unmarshaller.unmarshal(xmlStreamReader, MedicalCard.class);
            return root.getValue();
        }
        catch (JAXBException | XMLStreamException e) {
            throw new WebApplicationException(e);
        }
    }
}
