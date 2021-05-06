package com.fastcampus.javaallinone.project3.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @PostMapping("api/helloWorld")
    public String helloWorld(){
        return "hello world";
    }
}
