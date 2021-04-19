package com.example.study.repository;

import com.example.study.StudyApplicationTests;
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
        User user = new User();
        user.setAccount("test1");
        user.setEmail("testmail1");
        user.setPhoneNumber("testnum1");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("test1");

        userRepository.save(user);
        //System.out.println(newUser);
    }

    @Test
    public void read(){
        Optional<User> user = userRepository.findById(1L);

        user.ifPresent(selectUser -> {
            System.out.println("user : "+selectUser);
        });
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
