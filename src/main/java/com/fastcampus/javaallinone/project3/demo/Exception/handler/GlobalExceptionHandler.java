package com.fastcampus.javaallinone.project3.demo.Exception.handler;

import com.fastcampus.javaallinone.project3.demo.Exception.PersonNotFoundException;
import com.fastcampus.javaallinone.project3.demo.Exception.RenameNotPermittedException;
import com.fastcampus.javaallinone.project3.demo.Exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RenameNotPermittedException.class)
    public ErrorResponse handleRenameNoPermittedException(RenameNotPermittedException ex){
        return ErrorResponse.of(HttpStatus.BAD_REQUEST,ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PersonNotFoundException.class)
    public ErrorResponse handlePersonNotFoundException(PersonNotFoundException ex){
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = RuntimeException.class)
    public ErrorResponse handleRuntimeException(RuntimeException ex){
        log.error("Server Error : {}",ex.getMessage(),ex);
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error");
    }
}
