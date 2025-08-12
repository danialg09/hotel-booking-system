package com.hotel.web.controller;

import com.hotel.exception.BookingAlreadyExistsException;
import com.hotel.exception.EntityNotFoundException;
import com.hotel.web.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse notFound(EntityNotFoundException e) {
        log.error(e.getLocalizedMessage());

        return new ErrorResponse(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookingAlreadyExistsException.class)
    public ErrorResponse bookingError(BookingAlreadyExistsException e) {
        log.error(e.getLocalizedMessage());

        return new ErrorResponse(e.getLocalizedMessage());
    }
}
