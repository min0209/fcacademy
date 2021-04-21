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
        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc(phoneNumber);
        if(user != null){
            user.getOrderGroupList().stream().forEach(orderGroup -> {
                System.out.println("----------주문묶음----------");
                System.out.println("수령인 : "+orderGroup.getRevName());
                System.out.println("수령지 : "+orderGroup.getRevAddress());
                System.out.println("총금액 : "+orderGroup.getTotalPrice());
                System.out.println("총수량 : "+orderGroup.getTotalQuantity());

                orderGroup.getOrderDetailList().forEach(orderDetail -> {

                    System.out.println("----------주문상세----------");
                    System.out.println("파트너사 이름 : "+orderDetail.getItem().getPartner().getName());
                    System.out.println("파트너사 카테고리 : "+orderDetail.getItem().getPartner().getCategory().getTitle());
                    System.out.println("주문 상품 : "+orderDetail.getItem().getName());
                    System.out.println("고객센터 번호 : "+orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("주문의 상태 : "+orderDetail.getStatus());
                    System.out.println("도착예정일자 : "+orderDetail.getArrivalDate());

                });

            });
        }
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
