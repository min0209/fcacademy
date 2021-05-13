package com.fastcampus.javaallinone.project3.demo.controller;

import com.fastcampus.javaallinone.project3.demo.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
class PersonControllerTest {

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    private MockMvc mockMvc;

    @Test
    void beforeEach() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    void getPerson() throws Exception {
        beforeEach();
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("A"));
    }

    @Test
    void postPerson() throws Exception {
        beforeEach();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{  \n" +
                        "  \"name\": \"F\", \"age\" : 11, \"bloodType\" : \"A\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void modifyPerson() throws Exception {
        beforeEach();
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/person/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{  \n" +
                    "  \"name\": \"A\", \"age\" : 11, \"bloodType\" : \"O\"\n" +
                    "}"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void modifyName() throws Exception{
        beforeEach();
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name","modify"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void deletePerson() throws Exception{
        beforeEach();
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk());
        personRepository.findPeopleDeleted().forEach(System.out::println);
    }
}