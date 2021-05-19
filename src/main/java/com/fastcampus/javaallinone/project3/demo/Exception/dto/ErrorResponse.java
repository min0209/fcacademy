package com.fastcampus.javaallinone.project3.demo.Exception.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.lang.reflect.Field;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private int code;
    private String message;

    public static ErrorResponse of(HttpStatus status, String message){
        return new ErrorResponse(status.value(),message);
    }

    public static ErrorResponse of(HttpStatus status, FieldError fieldError){
        if(fieldError == null){
            return new ErrorResponse(status.value(),"invalid params");
        }else{
            return new ErrorResponse(status.value(),fieldError.getDefaultMessage());
        }
    }
}
