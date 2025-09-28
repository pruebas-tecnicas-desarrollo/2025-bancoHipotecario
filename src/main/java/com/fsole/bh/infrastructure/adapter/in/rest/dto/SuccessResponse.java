package com.fsole.bh.infrastructure.adapter.in.rest.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SuccessResponse<T> {
    String requestId;
    T data; // generic type: may represent a single Post or a collection of Post objects
}