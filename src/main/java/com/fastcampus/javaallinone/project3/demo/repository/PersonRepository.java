package com.fastcampus.javaallinone.project3.demo.repository;

import com.fastcampus.javaallinone.project3.demo.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person,Long> {

    List<Person> findByName(String name);

    List<Person> findByBlockIsNull();

    List<Person> findByBloodType(String bloodType);

    @Query(value = "select * from person where month_of_birthday = :monthOfBirthday and day_of_birthday = :dayOfBirthday",nativeQuery = true)
    List<Person> findByMonthOfBirthday(@Param("monthOfBirthday") int monthOfBirthday, @Param("dayOfBirthday") int dayOfBirthday);

    @Query(value = "select * from Person person where person.deleted = true",nativeQuery = true)
    List<Person> findPeopleDeleted();
}
