package org.example.exceptions;

public class EmailAlreadyExistsException extends GatePassException {
    public EmailAlreadyExistsException(String message) {

        super(message);
    }
}
