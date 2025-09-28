package com.fsole.bh.application.util;

import com.fsole.bh.domain.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostValidationUtil {
    public static void validateId(Long id) {
        if (id <= 0) {
            log.warn("invalid post id: "+ id);
            throw new InvalidRequestException("invalid post id: " + id);
        }
    }
}
