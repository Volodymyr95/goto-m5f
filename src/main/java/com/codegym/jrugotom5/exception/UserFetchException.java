package com.codegym.jrugotom5.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserFetchException extends RuntimeException {
    public UserFetchException(String message) {
        super(message);
    }
}
