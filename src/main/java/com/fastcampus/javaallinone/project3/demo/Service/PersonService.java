package com.fastcampus.javaallinone.project3.demo.Service;

import com.fastcampus.javaallinone.project3.demo.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.demo.domain.Person;
import com.fastcampus.javaallinone.project3.demo.domain.dto.Birthday;
import com.fastcampus.javaallinone.project3.demo.repository.BlockRepository;
import com.fastcampus.javaallinone.project3.demo.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BlockRepository blockRepository;


    public List<Person> getPeopleExcludeBlocks(){
        return personRepository.findByBlockIsNull();
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id){
        Person person = personRepository.findById(id).get();
        log.info("person : {}",person);
        return person;
    }

    @Transactional
    public List<Person> getPeopleByName(String name){
//        List<Person> people = personRepository.findAll();
//        return people.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList());
        return personRepository.findByName(name);
    }

    @Transactional
    public void put(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto personDto){
        Person personAtDb = personRepository.findById(id).orElseThrow(() -> new RuntimeException("id does not exist"));

        if(!personDto.getName().equals(personAtDb.getName())){
            throw new RuntimeException("Different name !!");
        }

        personAtDb.set(personDto);
        personRepository.save(personAtDb);
    }

    @Transactional
    public void modify(Long id, String name){
        Person personAtDb = personRepository.findById(id).orElseThrow(() -> new RuntimeException("id does not exist"));

        personAtDb.setName(name);
        personRepository.save(personAtDb);
    }

    @Transactional
    public void delete(Long id){
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("id does not exist"));
        person.setDeleted(true);
        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);
    }
}
