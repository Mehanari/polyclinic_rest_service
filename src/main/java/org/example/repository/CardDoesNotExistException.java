package org.example.repository;

public class CardDoesNotExistException extends Exception{
    public CardDoesNotExistException(String message) {
        super(message);
    }
}
