package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){

        String account = "test1";
        String password = "test password1";
        String status = "test status1";
        String email = "test mail1";
        String phoneNumber = "test num1";
        LocalDateTime registerAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "test1";

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registerAt);
        user.setCreatedAt(createdAt);
        user.setCreatedBy(createdBy);

        userRepository.save(user);
    }

    @Transactional
    @Test
    public void read(){
        String phoneNumber="test num1";
        Optional<User> user = Optional.ofNullable(userRepository.findFirstByPhoneNumberOrderByIdDesc(phoneNumber));
        System.out.println(user);
    }

    @Test
    public void update(){
        Optional<User> user = userRepository.findById(1L);

        user.ifPresent(selectUser -> {
            selectUser.setAccount("Q");
            selectUser.setEmail("W");
            selectUser.setPhoneNumber("E");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("R");

            userRepository.save(selectUser);
        });

    }

    @Test
    @Transactional
    public void delete(){
        Optional<User> user = userRepository.findById(1L);

        Assertions.assertTrue(user.isPresent());

        user.ifPresent(selectUser -> {
            userRepository.delete(selectUser);
        });
        Optional<User> deleteUser = userRepository.findById(1L);
        Assertions.assertFalse(deleteUser.isPresent());
    }
}
