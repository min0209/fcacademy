package com.fastcampus.javaallinone.project3.demo.controller;

import com.fastcampus.javaallinone.project3.demo.Service.PersonService;
import com.fastcampus.javaallinone.project3.demo.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.demo.domain.Person;
import com.fastcampus.javaallinone.project3.demo.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(value = "/{id}")
    public Person getPerson(@PathVariable Long id){
        return personService.getPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postPerson(@RequestBody Person person){
        personService.put(person);
        log.info("person : {}",personRepository.findAll());
    }

    @PutMapping(value = "/{id}")
    public void modifyPerson(@PathVariable Long id, @RequestBody PersonDto personDto){
        personService.modify(id,personDto);
        System.out.println(personRepository.findAll());
        log.info("person : {}",personRepository.findAll());
    }

    @PatchMapping(value = "/{id}")
    public void modifyPerson(@PathVariable Long id, String name){
        personService.modify(id,name);
        log.info("person : {}",personRepository.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public void deletePerson(@PathVariable Long id){
        personService.delete(id);
    }
}