package com.example.study.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;

public class UserRepositoryTest extends StudyApplicationTests{
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void create() {
		
		User user = new User();
		
		user.setAccount("TestUser01");
		user.setEmail("TestUser01@test.com");
		user.setPhoneNumber("000-1111-2222");
		user.setCreatedAt(LocalDateTime.now());
		user.setCreatedBy("admin");
		
		User newUser = userRepository.save(user);
		System.out.println("new user : "+newUser);
		
	}

	public void read() {

	}
	public void update() {
		
	}
	public void delete() {
		
	}

}
