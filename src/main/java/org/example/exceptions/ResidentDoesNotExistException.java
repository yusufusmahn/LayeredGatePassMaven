package org.example.exceptions;

public class ResidentDoesNotExistException extends GatePassException {
    public ResidentDoesNotExistException(String message) {
        super(message);
    }
}