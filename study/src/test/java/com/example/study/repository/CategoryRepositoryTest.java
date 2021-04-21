package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class CategoryRepositoryTest extends StudyApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create(){
        String type = "COMPUTER";
        String title = "computer";
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        Category category = new Category();
        category.setType(type);
        category.setTitle(title);
        category.setCreatedAt(createdAt);
        category.setCreatedBy(createdBy);

        //Category newCategory = categoryRepository.save(category);     error
        categoryRepository.save(category);
    }

    @Test
    public void read(){
        String type = "COMPUTER";
        Optional<Category> optionalCategory = categoryRepository.findByType(type);
        optionalCategory.ifPresent( selectCategory -> {

            Assertions.assertEquals(selectCategory.getType(),type);

            System.out.println(selectCategory.getId());
            System.out.println(selectCategory.getTitle());
            System.out.println(selectCategory.getType());
        });

    }
    public void update(){

    }
    public void delete(){

    }
}
