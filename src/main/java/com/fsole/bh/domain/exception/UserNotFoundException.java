package com.fsole.bh.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User id " + id + " not found");
    }
}
