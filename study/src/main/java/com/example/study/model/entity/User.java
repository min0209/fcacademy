package com.example.study.model.entity;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor                                      
@Entity // ==table
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String account;
	
	private String email;
	
	private String phoneNumber;
	
	private LocalDateTime createdAt;
	
	private String createdBy;
	
	private LocalDateTime updatedAt;
	
	private String updatedBy;	
	
}
