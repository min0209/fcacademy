package com.fastcampus.javaallinone.project3.demo.Exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonNotFoundException extends RuntimeException{
    private static final String MESSAGE = "Person Entity does not exist";
    public PersonNotFoundException(){
        super(MESSAGE);
        log.error(MESSAGE);
    }
}
