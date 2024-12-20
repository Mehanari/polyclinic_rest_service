package org.example.repository;

public class AppointmentDoesNotExistException extends Exception{
    public AppointmentDoesNotExistException(String message) {
        super(message);
    }
}
