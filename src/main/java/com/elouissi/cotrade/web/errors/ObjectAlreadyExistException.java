package com.elouissi.cotrade.web.errors;

public class ObjectAlreadyExistException extends RuntimeException {
    public ObjectAlreadyExistException(String message) {
        super(message);
    }
}
