package com.fastcampus.javaallinone.project3.demo.Service;

import com.fastcampus.javaallinone.project3.demo.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.demo.domain.Person;
import com.fastcampus.javaallinone.project3.demo.domain.dto.Birthday;
import com.fastcampus.javaallinone.project3.demo.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void getPerson(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("A")));

        Person person = personService.getPerson(1L);

        assertThat(person.getName()).isEqualTo("A");
    }

    @Test
    void getPeopleByName(){
        when(personRepository.findByName("A"))
                .thenReturn(Lists.newArrayList(new Person("A")));

        List<Person> people = personService.getPeopleByName("A");
        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("A");
    }

    @Test
    void getPersonIfNotFound(){
        when(personRepository.findById(10L))
                .thenReturn(Optional.empty());

        Person person = personService.getPerson(10L);
        assertThat(person).isNull();
    }

    @Test
    void put(){
        personService.put(mockPersonDto());

        verify(personRepository,times(1)).save(argThat(new IsPersonWillBeInserted()));
    }

    @Test
    void modifyIfPersonNotFound(){
        when(personRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.modify(10L,mockPersonDto())) ;
    }

    @Test
    void modifyIfNameIsDifferent(){
        when(personRepository.findById(10L))
                .thenReturn(Optional.of(new Person("H")));

        assertThrows(RuntimeException.class, () -> personService.modify(10L,mockPersonDto())) ;
    }

    @Test
    void modify(){
        when(personRepository.findById(10L))
                .thenReturn(Optional.of(new Person("V")));

        personService.modify(10L,mockPersonDto());
        //verify(personRepository,times(1)).save(any(Person.class));
        verify(personRepository,times(1)).save(argThat(new IsPersonWillBeUpdated()));

    }

    @Test
    void modifyByNameIfPersonNotFound(){
        when(personRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.modify(10L,"K"));
    }

    @Test
    void modifyByName(){
        when(personRepository.findById(1L)).thenReturn(Optional.of(new Person("V")));
        personService.modify(1L,"K");
        verify(personRepository,times(1)).save(argThat(new IsNameWillBeUpdated()));

    }

    @Test
    void deleteIfPersonNotFound(){
        when(personRepository.findById(10L))
                .thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> personService.delete(10L));

    }

    @Test
    void delete(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("A")));
        personService.delete(1L);
        verify(personRepository,times(1)).save(argThat(new IsPersonWillBeDeleted()));
    }

    private PersonDto mockPersonDto(){
        return PersonDto.of("V","v hob","v add", LocalDate.now(),"v job","v num");
    }

    private class IsPersonWillBeInserted implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.getName().equals("V")
                    && person.getHobby().equals("v hob")
                    && person.getAddress().equals("v add")
                    && person.getBirthday().equals(Birthday.of(LocalDate.now()))
                    && person.getJob().equals("v job")
                    && person.getPhoneNumber().equals("v num");
        }
    }

    private class IsNameWillBeUpdated implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.getName().equals("K");
        }
    }

    private class IsPersonWillBeUpdated implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.getName().equals("V")
                    && person.getHobby().equals("v hob")
                    && person.getAddress().equals("v add")
                    && person.getBirthday().equals(Birthday.of(LocalDate.now()))
                    && person.getJob().equals("v job")
                    && person.getPhoneNumber().equals("v num");
        }
//        private boolean equals(Object actual, Object expected){
//            return expected.equals(actual);
//        }
    }

    private class IsPersonWillBeDeleted implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.isDeleted();
        }
    }
}