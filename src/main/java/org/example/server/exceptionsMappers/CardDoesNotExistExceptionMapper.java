package org.example.server.exceptionsMappers;

import jakarta.ws.rs.core.Response;
import org.example.repository.CardDoesNotExistException;

public class CardDoesNotExistExceptionMapper extends FormatedExceptionMapper<CardDoesNotExistException> {
    @Override
    public Response toResponse(CardDoesNotExistException exception) {
        return toFormatedResponse(exception, Response.Status.NOT_FOUND);
    }
}
