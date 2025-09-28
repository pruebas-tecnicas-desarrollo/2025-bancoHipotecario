package com.fsole.bh.infrastructure.adapter.in.rest.util;

import com.fsole.bh.infrastructure.adapter.in.rest.dto.ErrorResponse;
import com.fsole.bh.infrastructure.adapter.in.rest.dto.SuccessResponse;
import org.slf4j.MDC;

public class ResponseBuilder {

    private ResponseBuilder() {}

    public static <T> SuccessResponse<T> success(T data) {
        return SuccessResponse.<T>builder()
                .requestId(MDC.get("requestId"))
                .data(data)
                .build();
    }

    public static ErrorResponse error(String message) {
        return ErrorResponse.builder()
                .message(message)
                .requestId(MDC.get("requestId"))
                .build();
    }
}
