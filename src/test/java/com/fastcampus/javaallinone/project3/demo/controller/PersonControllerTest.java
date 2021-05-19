package com.fastcampus.javaallinone.project3.demo.controller;

import com.fastcampus.javaallinone.project3.demo.Exception.handler.GlobalExceptionHandler;
import com.fastcampus.javaallinone.project3.demo.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.demo.domain.Person;
import com.fastcampus.javaallinone.project3.demo.domain.dto.Birthday;
import com.fastcampus.javaallinone.project3.demo.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
@SpringBootTest
class PersonControllerTest {

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void beforeEach() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .alwaysDo(print())
                .build();
    }

    @Test
    void getPerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("A"))
                .andExpect(jsonPath("hobby").isEmpty())
                .andExpect(jsonPath("address").isEmpty())
                .andExpect(jsonPath("birthday").value("1111-01-01"))
                .andExpect(jsonPath("job").isEmpty())
                .andExpect(jsonPath("phoneNumber").isEmpty())
                .andExpect(jsonPath("deleted").value(false))
                .andExpect(jsonPath("age").isNumber())
                .andExpect(jsonPath("birthdayToday").isBoolean());
    }

    @Test
    void postPerson() throws Exception {
        PersonDto dto = PersonDto.of("F","f hobby","f address",LocalDate.now(),"f job","f phone number");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(dto)))
                .andExpect(status().isCreated());

        Person result = personRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).get(0);
        assertAll(
                () -> assertThat(result.getName()).isEqualTo("F"),
                () -> assertThat(result.getHobby()).isEqualTo("f hobby"),
                () -> assertThat(result.getAddress()).isEqualTo("f address"),
                () -> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                () -> assertThat(result.getJob()).isEqualTo("f job"),
                () -> assertThat(result.getPhoneNumber()).isEqualTo("f phone number")
        );
    }
    @Test
    void postPersonIfNameIsNull() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.setName("");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(personDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("Name required"));
    }

    @Test
    void postPersonIfNameIsEmpty() throws Exception {
        PersonDto personDto = new PersonDto();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(personDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("Name required"));
    }

    @Test
    void postPersonIfNameIsBlankString() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.setName(" ");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(personDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("Name required"));
    }

    @Test
    void modifyPerson() throws Exception {
        PersonDto dto = PersonDto.of("A","hob","add",LocalDate.now(),"job","num");
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/person/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(dto)))
            .andExpect(status().isOk());

        Person result = personRepository.findById(1L).get();
        assertAll(
            () -> assertThat(result.getName()).isEqualTo("A"),
            () -> assertThat(result.getHobby()).isEqualTo("hob"),
            () -> assertThat(result.getAddress()).isEqualTo("add"),
            () -> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
            () -> assertThat(result.getJob()).isEqualTo("job"),
            () -> assertThat(result.getPhoneNumber()).isEqualTo("num")
        );
    }
    @Test
    void modifyPersonIfNameIsDifferent() throws Exception {
        PersonDto dto = PersonDto.of("F","f hobby","f address",LocalDate.now(),"f job","f phone number");
            mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("Rename not permitted"));
    }

    @Test
    void modifyPersonIfPersonNotFound() throws Exception {
        PersonDto dto = PersonDto.of("F","f hobby","f address",LocalDate.now(),"f job","f phone number");
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("Person Entity does not exist"));
    }

    @Test
    void modifyName() throws Exception{
        Long testID = 1L;
        String testName = "modify";
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/"+testID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name",testName))
                .andExpect(status().isOk());
        assertTrue(personRepository.findById(testID).get().getName().equals(testName));
    }
    @Test
    void deletePerson() throws Exception{
        Long testID = 1L;
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/"+testID))
                .andExpect(status().isOk());

        assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(testID)));
    }

    @Test
    void checkJsonString() throws JsonProcessingException {
        PersonDto dto = new PersonDto();
        dto.setName("aaa");
        dto.setBirthday(LocalDate.now());
        dto.setAddress("bb");

        System.out.println(">>>     "+toJsonString(dto));

    }

    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }
}