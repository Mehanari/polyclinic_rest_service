package org.example.server.writer;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.example.Constants;
import org.example.entity.MedicalCard;
import org.example.entity.MedicalCards;
import org.example.entity.ObjectFactory;
import org.example.server.reader.MedicalCardXMLMessageBodyReader;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class MedicalCardListXMLMessageBodyWriter implements MessageBodyWriter<List<MedicalCard>> {
    private final JAXBContext jaxbContext;
    private static final QName qName = new QName(Constants.MEDICAL_CARD_NS, "medicalCards");

    public MedicalCardListXMLMessageBodyWriter() {
        try {
            jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        if (!mediaType.isCompatible(MediaType.APPLICATION_XML_TYPE)) {
            return false;
        }
        if (List.class.isAssignableFrom(aClass) && type instanceof ParameterizedType) {
            Type actualType = ((ParameterizedType) type).getActualTypeArguments()[0];
            return actualType == MedicalCards.class;
        }

        return false;
    }

    @Override
    public void writeTo(List<MedicalCard> medicalCards, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");

        try{
            MedicalCards cards = new MedicalCards();
            cards.setMedicalCard(medicalCards);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper", new CustomNamespacePrefixMapper());
            JAXBElement<MedicalCards> root = new JAXBElement<>(qName, MedicalCards.class, cards);
            marshaller.marshal(root, writer);
        } catch (JAXBException e) {
            throw new WebApplicationException(e);
        }
        writer.close();
    }
}
