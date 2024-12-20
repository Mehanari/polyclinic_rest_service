package org.example.server.exceptionsMappers;

import jakarta.ws.rs.core.Response;
import org.example.repository.AppointmentResultDoesNotExistException;

public class AppointmentResultDoesNotExistExceptionMapper extends FormatedExceptionMapper<AppointmentResultDoesNotExistException> {
    @Override
    public Response toResponse(AppointmentResultDoesNotExistException exception) {
        return toFormatedResponse(exception, Response.Status.NOT_FOUND);
    }
}
