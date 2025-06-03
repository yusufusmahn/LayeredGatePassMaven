package org.example.exceptions;

public class InvalidLoginException extends GatePassException {
    public InvalidLoginException(String message) {

        super(message);
    }
}