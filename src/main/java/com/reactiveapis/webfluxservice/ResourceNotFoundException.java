package com.reactiveapis.webfluxservice;

public class ResourceNotFoundException extends RuntimeException {
    private String message;
    private Throwable ex;

    public ResourceNotFoundException(String message, Throwable ex) {
        super(message, ex);
        this.message = message;
        this.ex = ex;
    }

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
