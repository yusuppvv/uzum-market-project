package com.company.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
