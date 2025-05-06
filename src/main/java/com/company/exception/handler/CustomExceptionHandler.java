package com.company.exception.handler;


import com.company.component.ApiResponse;
import com.company.exception.classes.AppBadRequestException;
import com.company.exception.classes.BadRequestException;
import com.company.exception.classes.ItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handlerItemNotFoundException(ItemNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(400, e.getMessage() , null));
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handlerBadRequestException(BadRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(400, e.getMessage() , null));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiResponse<String>> handlerPSQLException(ItemNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ApiResponse<>(400, e.getMessage() , null));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handlerException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(400, e.getMessage() , null));

    }

    @ExceptionHandler(AppBadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handlerException(AppBadRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(400, e.getMessage() , null)
                );

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handlerException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(400, e.getMessage(), null)
                );
    }
}
