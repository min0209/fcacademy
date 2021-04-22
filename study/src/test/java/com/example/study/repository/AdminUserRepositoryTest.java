package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.AdminUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class AdminUserRepositoryTest extends StudyApplicationTests {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Test
    public void create(){

        AdminUser adminUser = new AdminUser();

        adminUser.setAccount("AdminUser3");
        adminUser.setPassword("AdminUser3");
        adminUser.setStatus("REGISTERED");
        adminUser.setRole("PARTNER");
        //adminUser.setCreatedAt(LocalDateTime.now());
        //adminUser.setCreatedBy("AdminServer");

        AdminUser newAdminUser = adminUserRepository.save(adminUser);
        newAdminUser.setAccount("CHANGE");
        adminUserRepository.save(newAdminUser);
    }
}
