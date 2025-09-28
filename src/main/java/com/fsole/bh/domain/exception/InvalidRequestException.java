package com.fsole.bh.domain.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
        super("Invalid post id");
    }
}