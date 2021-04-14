package com.example.study.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

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
	
	@Test
	public void read() {
		Optional<User> user = userRepository.findById(2L);
		
		user.ifPresent(selectUser -> {
			System.out.println("user : "+selectUser);			
		});
		
	}
	@Test
	public void update() {
		
		Optional<User> user = userRepository.findById(2L);
		
		user.ifPresent(selectUser -> {
			selectUser.setAccount("update");
			selectUser.setCreatedAt(LocalDateTime.now());
			selectUser.setCreatedBy("update method()");
			
			userRepository.save(selectUser);
		});
		
	}
	@Test
	public void delete() {
		Optional<User> user = userRepository.findById(2L);
		
		user.ifPresent(selectUser -> {
			userRepository.delete(selectUser);			
		});
		
		Optional<User> deleteUser = userRepository.findById(2L);
		
		if(deleteUser.isPresent()) {
			System.out.println("데이터 존재 "+deleteUser.get());
		}else {
			System.out.println("데이터 삭제 데이터 없음");
		}
	}

}
