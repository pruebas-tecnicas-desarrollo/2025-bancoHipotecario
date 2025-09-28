package com.fsole.bh.domain.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("Post id " + id + " not found");
    }
}
