package org.example.exceptions;

public class AccessCodeNotFoundException extends RuntimeException {
  public AccessCodeNotFoundException(String message) {

    super(message);
  }
}
