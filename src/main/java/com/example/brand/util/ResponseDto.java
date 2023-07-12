package com.example.brand.util;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseDto {

    private final Integer code;

    private final String message;

    private final ResponseBody<?> body;
}
