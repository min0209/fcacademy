package com.fastcampus.javaallinone.project3.demo.controller;

import com.fastcampus.javaallinone.project3.demo.Exception.PersonNotFoundException;
import com.fastcampus.javaallinone.project3.demo.Exception.RenameNotPermittedException;
import com.fastcampus.javaallinone.project3.demo.Exception.dto.ErrorResponse;
import com.fastcampus.javaallinone.project3.demo.Service.PersonService;
import com.fastcampus.javaallinone.project3.demo.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.demo.domain.Person;
import com.fastcampus.javaallinone.project3.demo.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/{id}")
    public Person getPerson(@PathVariable Long id){
        return personService.getPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postPerson(@RequestBody PersonDto personDto){
        personService.put(personDto);
    }

    @PutMapping(value = "/{id}")
    public void modifyPerson(@PathVariable Long id, @RequestBody PersonDto personDto){
        personService.modify(id,personDto);
    }

    @PatchMapping(value = "/{id}")
    public void modifyPerson(@PathVariable Long id, String name){
        personService.modify(id,name);
    }

    @DeleteMapping(value = "/{id}")
    public void deletePerson(@PathVariable Long id){
        personService.delete(id);
    }

}