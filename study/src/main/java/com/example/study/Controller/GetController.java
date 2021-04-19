package com.example.study.controller;

import com.example.study.model.SearchParam;
import com.example.study.model.entity.User;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class GetController {

    @RequestMapping(method = RequestMethod.GET, path = "/getMethod")
    public String getRequest(){
        return "hi getMethod";
    }

    @GetMapping("/getParameter")
    public String getParameter(@RequestParam String id, @RequestParam(name="password") String pwd){
        String password = "p";
        System.out.println("id : "+id);
        System.out.println("password : "+password);

        return id+password;
    }

    @GetMapping("/getMultiParameter")
    public SearchParam getMultiParameter(SearchParam searchParam){
        System.out.println(searchParam.getAccount());
        System.out.println(searchParam.getEmail());
        System.out.println(searchParam.getPage());

        return searchParam;
    }
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/create")
    public void create(){
        User user = new User();
        user.setAccount("A");
        user.setEmail("B");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("C");
        user.setPhoneNumber("D");
        userRepository.save(user);
        System.out.println("create?");
    }
}
