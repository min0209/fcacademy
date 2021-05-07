package com.fastcampus.javaallinone.project3.demo.Service;

import com.fastcampus.javaallinone.project3.demo.domain.Block;
import com.fastcampus.javaallinone.project3.demo.domain.Person;
import com.fastcampus.javaallinone.project3.demo.repository.BlockRepository;
import com.fastcampus.javaallinone.project3.demo.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private PersonService personService;

    @Test
    void getPeopleExcludeBlocks(){
        givenPeople();

        List<Person> result = personService.getPeopleExcludeBlocks();
        result.forEach(System.out::println);
    }

    @Test
    void cascadeTest(){
        givenPeople();
        List<Person> result = personRepository.findAll();
        result.forEach(System.out::println);

        Person person = result.get(3);
        person.getBlock().setStartDate(LocalDate.now());
        person.getBlock().setEndDate(LocalDate.now());

        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);

//        personRepository.delete(person);
//        personRepository.findAll().forEach(System.out::println);
//        blockRepository.findAll().forEach(System.out::println);

        person.setBlock(null);
        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);
        blockRepository.findAll().forEach(System.out::println);

    }

    @Test
    void getPerson(){
        givenPeople();
        personService.getPerson(3L);
    }

    private void givenBlockPerson(String name, int age, String bloodType){
        Person blockPerson = new Person(name,age,bloodType);
        blockPerson.setBlock(new Block(name));

        personRepository.save(blockPerson);
    }
    private void givenPeople() {
        givenBlockPerson("A",4,"A");
        givenBlockPerson("B",3,"B");
        givenBlockPerson("O",2,"O");
        givenBlockPerson("A",1,"AB");
    }

    private void givenPerson(String name, int age, String bloodType) {
        personRepository.save(new Person(name,age,bloodType));
    }
}