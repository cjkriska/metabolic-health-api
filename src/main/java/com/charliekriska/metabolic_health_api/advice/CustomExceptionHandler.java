package com.charliekriska.metabolic_health_api.advice;

import com.charliekriska.metabolic_health_api.exception.BadRequestException;
import com.charliekriska.metabolic_health_api.exception.InvalidCredentialsException;
import com.charliekriska.metabolic_health_api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public Map<String, String> handleBadRequestException(BadRequestException exception) {
        Map<String, String> map =  new HashMap<>();
        map.put("message", exception.getMessage());
        return map;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        return map;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    public Map<String, String> handleInvalidCredentialsException(InvalidCredentialsException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        return map;
    }

}
