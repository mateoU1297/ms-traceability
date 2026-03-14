package com.pragma.traceability.domain.exception;

public class UnauthorizedTraceAccessException extends RuntimeException {

    public UnauthorizedTraceAccessException(String message) {
        super(message);
    }
}
