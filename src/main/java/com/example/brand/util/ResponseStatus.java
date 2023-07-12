package com.example.brand.util;

import lombok.Getter;

@Getter
public enum ResponseStatus {

    OK(200,"OK"),
    EXCEPTION(500,"EXCEPTION");

    private final Integer statusCode;
    private final String statusMessage;

    ResponseStatus(Integer statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
