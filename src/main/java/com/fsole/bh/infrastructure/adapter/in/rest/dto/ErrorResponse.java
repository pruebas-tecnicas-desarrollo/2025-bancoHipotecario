package com.fsole.bh.infrastructure.adapter.in.rest.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorResponse {
    String message;
    String requestId;
}