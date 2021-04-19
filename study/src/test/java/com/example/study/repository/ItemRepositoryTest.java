package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ItemRepositoryTest extends StudyApplicationTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create(){
        Item item = new Item();

        item.setName("test item");
        item.setPrice(12345);
        item.setContent("test item content");

        itemRepository.save(item);
    }

    @Test
    public void read(){
        Optional<Item> item = itemRepository.findById(1L);
        System.out.println(item);
    }
}
