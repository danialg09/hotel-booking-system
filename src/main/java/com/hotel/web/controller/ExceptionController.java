package com.hotel.web.controller;

import com.hotel.exception.AlreadyExistsException;
import com.hotel.exception.EntityNotFoundException;
import com.hotel.exception.RefreshTokenException;
import com.hotel.web.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse notFoundException(EntityNotFoundException e) {
        log.error(e.getLocalizedMessage());
        return buildResponse(e);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistsException.class)
    public ErrorResponse alreadyExistsException(AlreadyExistsException e) {
        log.error(e.getLocalizedMessage());
        return buildResponse(e);
    }

    @ExceptionHandler(value = RefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse refreshTokenException(RefreshTokenException e) {
        return buildResponse(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    private ErrorResponse buildResponse(Exception e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .description(e.getLocalizedMessage())
                .build();
    }
}
