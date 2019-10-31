package com.objectway.stage.examples.weatherservice.advice;

import com.objectway.stage.examples.weatherservice.dto.ErrorDTO;
import com.objectway.stage.examples.weatherservice.dto.ValidationErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class WeatherServiceControllerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(final Exception ex) {
        return new ErrorDTO(ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : null);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleServletRequestBinding(final ServletRequestBindingException ex) {
        return new ErrorDTO(ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO handleMethodArgumentNotValid(final MethodArgumentNotValidException exception) {
        return new ValidationErrorDTO("Validation error", exception.getBindingResult());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO handleConstraintViolationException(final ConstraintViolationException exception) {
        return new ValidationErrorDTO("Validation error", exception.getConstraintViolations());
    }
}
