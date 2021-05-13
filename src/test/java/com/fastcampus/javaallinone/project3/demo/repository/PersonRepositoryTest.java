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

        List<Person> result = personRepository.findByMonthOfBirthday(5, 5);
        result.forEach(System.out::println);

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("B");
        assertThat(result.get(1).getName()).isEqualTo("E");

    }
}