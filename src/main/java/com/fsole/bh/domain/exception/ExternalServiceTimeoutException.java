package com.fsole.bh.domain.exception;

public class ExternalServiceTimeoutException extends RuntimeException {
    public ExternalServiceTimeoutException(String url) {
        super("Timeout while waiting for external service: " + url);
    }
}
