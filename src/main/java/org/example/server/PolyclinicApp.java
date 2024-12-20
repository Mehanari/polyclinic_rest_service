package org.example.server;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.example.Constants;
import org.example.repository.AppointmentResultDoesNotExistException;
import org.example.repository.CardDoesNotExistException;
import org.example.server.controller.MedicalCardsController;
import org.example.server.exceptionsMappers.AppointmentDoesNotExistExceptionMapper;
import org.example.server.exceptionsMappers.AppointmentResultDoesNotExistExceptionMapper;
import org.example.server.exceptionsMappers.CardDoesNotExistExceptionMapper;
import org.example.server.exceptionsMappers.FormatedExceptionMapper;
import org.example.server.reader.MedicalCardJSONMessageBodyReader;
import org.example.server.reader.MedicalCardXMLMessageBodyReader;
import org.example.server.writer.MedicalCardListJSONMessageBodyWriter;
import org.example.server.writer.MedicalCardListXMLMessageBodyWriter;

import java.util.Set;

@ApplicationPath(Constants.APPLICATION_PATH)
public class PolyclinicApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(
                MedicalCardsController.class,
                MedicalCardJSONMessageBodyReader.class,
                MedicalCardXMLMessageBodyReader.class,
                MedicalCardListXMLMessageBodyWriter.class,
                MedicalCardListJSONMessageBodyWriter.class,
                AppointmentDoesNotExistExceptionMapper.class,
                CardDoesNotExistExceptionMapper.class,
                AppointmentResultDoesNotExistExceptionMapper.class
        );
    }
}
