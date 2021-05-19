package com.fastcampus.javaallinone.project3.demo.Service;

import com.fastcampus.javaallinone.project3.demo.Exception.PersonNotFoundException;
import com.fastcampus.javaallinone.project3.demo.Exception.RenameNotPermittedException;
import com.fastcampus.javaallinone.project3.demo.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.demo.domain.Person;
import com.fastcampus.javaallinone.project3.demo.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional(readOnly = true)
    public Person getPerson(Long id){
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Person> getPeopleByName(String name){
        return personRepository.findByName(name);
    }

    @Transactional
    public void put(PersonDto personDto){
        Person person = new Person();
        person.set(personDto);
        person.setName(personDto.getName());

        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto personDto){
        Person personAtDb = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        if(!personDto.getName().equals(personAtDb.getName())){
            throw new RenameNotPermittedException();
        }

        personAtDb.set(personDto);
        personRepository.save(personAtDb);
    }

    @Transactional
    public void modify(Long id, String name){
        Person personAtDb = personRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);

        personAtDb.setName(name);
        personRepository.save(personAtDb);
    }

    @Transactional
    public void delete(Long id){
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        person.setDeleted(true);
        personRepository.save(person);
    }
}
