package com.unicap.tcc.usability.api.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource){
        super("Cannot find ".concat(resource));
    }
}
