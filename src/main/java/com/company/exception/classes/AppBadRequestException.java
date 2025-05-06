package com.company.exception.classes;

public class AppBadRequestException extends RuntimeException {

    public AppBadRequestException(String s) {
        super(s);
    }
}
