package org.example.server.exceptionsMappers;

import jakarta.ws.rs.core.Response;
import org.example.repository.AppointmentDoesNotExistException;

public class AppointmentDoesNotExistExceptionMapper extends FormatedExceptionMapper<AppointmentDoesNotExistException> {
    @Override
    public Response toResponse(AppointmentDoesNotExistException exception) {
        return toFormatedResponse(exception, Response.Status.NOT_FOUND);
    }
}
