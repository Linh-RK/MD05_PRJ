package com.ra.md05_project.exception;

public class HttpBadRequest extends RuntimeException {
    public HttpBadRequest(String message) {
        super(message);
    }
}
