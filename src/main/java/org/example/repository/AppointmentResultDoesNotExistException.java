package org.example.repository;

public class AppointmentResultDoesNotExistException extends Exception{
    public AppointmentResultDoesNotExistException(String message) {
        super(message);
    }
}
