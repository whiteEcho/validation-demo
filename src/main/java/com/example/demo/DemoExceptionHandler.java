package com.example.demo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class DemoExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse constraintViolationExceptionHandler(
            ConstraintViolationException exception
    ) {
        return ErrorResponse.of(
                "Validation error.",
                exception.getMessage()
        );
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        return super.handleExceptionInternal(
                ex,
                ErrorResponse.of("Validation error.", ex.getFieldError().getField()),
                null,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse exceptionHandler(
            Exception exception
    ) {
        return ErrorResponse.of(
                exception.getClass().getName(),
                exception.getMessage()
        );
    }

    @Getter
    @AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
    private static class ErrorResponse {

        private final String error;

        private final String msg;
    }
}
