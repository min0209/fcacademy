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
        person.setName("F");
        person.setAge(11);

        personRepository.save(person);

        List<Person> people = personRepository.findByName("F");
        people.forEach(System.out::println);

        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("F");
        assertThat(people.get(0).getAge()).isEqualTo(11);

        personRepository.delete(person);
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
        List<Person> result = personRepository.findByBloodType("O");

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("C");
        assertThat(result.get(1).getName()).isEqualTo("E");
    }

    @Test
    void findByMonthOfBirthday(){

        List<Person> result = personRepository.findByMonthOfBirthday(5,5);
        result.forEach(System.out::println);

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("B");
        assertThat(result.get(1).getName()).isEqualTo("E");

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