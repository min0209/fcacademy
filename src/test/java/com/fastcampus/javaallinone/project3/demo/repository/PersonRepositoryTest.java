package com.fastcampus.javaallinone.project3.demo.repository;

import com.fastcampus.javaallinone.project3.demo.domain.Person;
import com.fastcampus.javaallinone.project3.demo.domain.dto.Birthday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud(){
        Person person = new Person();
        person.setName("A");
        person.setAge(1);
        personRepository.save(person);
        System.out.println(personRepository.findAll());
        List<Person> people = personRepository.findAll();
        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("A");
        assertThat(people.get(0).getAge()).isEqualTo(1);
    }
    void constructorTest(){
        Person person = new Person("a",2,"A");
    }
    @Test
    void hashCodeAndEquals(){
        Person person1 = new Person("B",2,"B");
        Person person2 = new Person("B",2,"B");

        System.out.println(person1.equals(person2));
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());


        Map<Person,Integer> map = new HashMap<>();
        map.put(person1,person1.getAge());

        System.out.println(map);
        System.out.println(map.get(person2));
    }

    @Test
    void findByBloodType(){
        givenPeople();
        //givenPerson("D",5,"A");

        List<Person> result = personRepository.findByBloodType("A");
        result.forEach(System.out::println);
    }

    @Test
    void findByMonthOfBirthday(){

        givenPeople();
        List<Person> result = personRepository.findByMonthOfBirthday(2,4);
        result.forEach(System.out::println);

    }

    private void givenPeople() {
        givenPerson("A",4,"A",LocalDate.of(1111,1,1));
        givenPerson("B",3,"B",LocalDate.of(1112,2,2));
        givenPerson("O",2,"O",LocalDate.of(1113,3,3));
        givenPerson("AB",1,"AB",LocalDate.of(1114,2,4));
    }

    private void givenPerson(String name, int age, String bloodType, LocalDate birthday) {
        Person person = new Person(name,age,bloodType);
        person.setBirthday(new Birthday(birthday.getYear(),birthday.getMonthValue(),birthday.getDayOfMonth()));
        personRepository.save(person);
    }

}