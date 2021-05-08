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

    @Test
    void getPeopleByName(){
        givenPeople();
        List<Person> people = personService.getPeopleByName("A");
        people.forEach(System.out::println);
    }

    @Test
    void getPeopleByBloodType(){
        givenPeople();
        //givenPerson("D",9,"A");

        List<Person> result = personService.getPeopleByBloodType("A");
        result.forEach(System.out::println);
    }

    @Test
    void getPeopleByBirthday(){

        givenPeople();
        List<Person> result = personService.getPeopleByBirthday(LocalDate.of(1111,1,1),LocalDate.of(1112,1,1));
        result.forEach(System.out::println);

    }

    private void givenBlockPerson(String name, int age, String bloodType){
        Person blockPerson = new Person(name,age,bloodType);
        blockPerson.setBlock(new Block(name));

        personRepository.save(blockPerson);
    }
    private void givenPeople() {
        givenPerson("A",4,"A",LocalDate.of(1111,1,1));
        givenPerson("B",3,"B",LocalDate.of(1111,2,2));
        givenPerson("O",2,"O",LocalDate.of(1113,3,3));
        givenPerson("A",1,"AB",LocalDate.of(1115,5,5));
    }

    private void givenPerson(String name, int age, String bloodType,LocalDate birthday) {
        Person person = new Person(name,age,bloodType);
        person.setBirthday(birthday);
        personRepository.save(person);
    }
}