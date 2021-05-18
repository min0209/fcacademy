package com.fastcampus.javaallinone.project3.demo.Exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RenameNotPermittedException extends RuntimeException{
    private static final String MESSAGE = "Rename not permitted";
    public RenameNotPermittedException(){
        super(MESSAGE);
        log.error(MESSAGE);
    }
}
