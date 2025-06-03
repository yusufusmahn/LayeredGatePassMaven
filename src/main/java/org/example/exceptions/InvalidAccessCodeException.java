package org.example.exceptions;

public class InvalidAccessCodeException extends GatePassException {
    public InvalidAccessCodeException(String message) {
        super(message);
    }
}