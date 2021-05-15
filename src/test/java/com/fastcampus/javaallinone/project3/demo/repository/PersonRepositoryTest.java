package com.fastcampus.javaallinone.project3.demo.repository;

import com.fastcampus.javaallinone.project3.demo.domain.Person;
import com.fastcampus.javaallinone.project3.demo.domain.dto.Birthday;
import org.assertj.core.api.AssertionsForClassTypes;
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
    void crud() {
        Person person = new Person();
        person.setName("F");

        personRepository.save(person);

        List<Person> people = personRepository.findByName("F");
        people.forEach(System.out::println);

        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("F");
        //assertThat(people.get(0).getAge()).isEqualTo(11);

        personRepository.delete(person);
    }

    @Test
    void findByMonthOfBirthday() {

        List<Person> person = personRepository.findByMonthOfBirthday(5);
        person.forEach(System.out::println);

        assertThat(person.size()).isEqualTo(2);
        assertThat(person.get(0).getName()).isEqualTo("B");
        assertThat(person.get(1).getName()).isEqualTo("E");

    }

    @Test
    void findByName(){
        List<Person> people = personRepository.findByName("R");
        assertThat(people.size()).isEqualTo(1);

        Person person = people.get(0);
        assertAll(
                () -> AssertionsForClassTypes.assertThat(person.getName()).isEqualTo("R"),
                () -> AssertionsForClassTypes.assertThat(person.getHobby()).isEqualTo("r hob"),
                () -> AssertionsForClassTypes.assertThat(person.getAddress()).isEqualTo("r add"),
                () -> AssertionsForClassTypes.assertThat(person.getBirthday()).isEqualTo(Birthday.of(LocalDate.of(1118,8,8))),
                () -> AssertionsForClassTypes.assertThat(person.getJob()).isEqualTo("r job"),
                () -> AssertionsForClassTypes.assertThat(person.getPhoneNumber()).isEqualTo("r num")
        );
    }

    @Test
    void findByNameIfDeleted(){
        List<Person> people = personRepository.findByName("T");
        assertThat(people.size()).isEqualTo(0);
    }

    @Test
    void findPeopleDeleted(){
        List<Person> people = personRepository.findPeopleDeleted();
        assertThat(people.get(0).getName()).isEqualTo("T");
        people.forEach(System.out::println);
    }
}