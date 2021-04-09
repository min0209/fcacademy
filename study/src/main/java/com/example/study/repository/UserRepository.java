package com.example.study.repository;

import org.springframework.stereotype.Repository;
import com.example.study.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

}
