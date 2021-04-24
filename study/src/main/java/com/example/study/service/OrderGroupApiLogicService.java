package com.example.study.service;

import com.example.study.controller.ifs.CrudInterface;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderGroupApiRequest;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.repository.OrderGroupRepository;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderGroupApiLogicService implements CrudInterface<OrderGroupApiRequest, OrderGroupApiResponse> {

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> request) {

        OrderGroupApiRequest body = request.getData();

        OrderGroup orderGroup = OrderGroup.builder()
                .status(body.getStatus())
                .orderType(body.getOrderType())
                .revAddress(body.getRevAddress())
                .revName(body.getRevName())
                .paymentType(body.getPaymentType())
                .totalPrice(body.getTotalPrice())
                .totalQuantity(body.getTotalQuantity())
                .arrivalDate(body.getArrivalDate())
                .user(userRepository.getOne(body.getUserId()))
                .build();
        orderGroupRepository.save(orderGroup);

        return response(orderGroup);
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {

        Optional<OrderGroup> body = orderGroupRepository.findById(id);
        return body.map(orderGroup -> response(orderGroup))
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> request) {

        OrderGroupApiRequest body = request.getData();
        return orderGroupRepository.findById(body.getId()).map(orderGroup -> {
            orderGroup
                    .setStatus(body.getStatus())
                    .setOrderType(body.getOrderType())
                    .setRevAddress(body.getRevAddress())
                    .setRevName(body.getRevName())
                    .setPaymentType(body.getPaymentType())
                    .setTotalPrice(body.getTotalPrice())
                    .setTotalQuantity(body.getTotalQuantity())
                    .setArrivalDate(body.getArrivalDate())
                    .setUser(userRepository.getOne(body.getUserId()));
            orderGroupRepository.save(orderGroup);
            return orderGroup;
        })
                .map(orderGroup -> response(orderGroup))
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return orderGroupRepository.findById(id)
                .map(orderGroup -> {
                    orderGroupRepository.delete(orderGroup);
                    return Header.OK();
                }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private Header<OrderGroupApiResponse> response(OrderGroup body){
        OrderGroupApiResponse orderGroupApiResponse = OrderGroupApiResponse.builder()
                .id(body.getId())
                .status(body.getStatus())
                .orderType(body.getOrderType())
                .revAddress(body.getRevAddress())
                .revName(body.getRevName())
                .paymentType(body.getPaymentType())
                .totalPrice(body.getTotalPrice())
                .totalQuantity(body.getTotalQuantity())
                .arrivalDate(body.getArrivalDate())
                .userId(body.getUser().getId())
                .build();

        return Header.OK(orderGroupApiResponse);
    }
}
