package com.example.study.service;

import com.example.study.controller.ifs.CrudInterface;
import com.example.study.model.entity.Item;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ItemApiLogicService extends BaseService<ItemApiRequest,ItemApiResponse,Item> {

    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {
        ItemApiRequest body = request.getData();

        Item item = Item.builder()
                .status(body.getStatus())
                .name(body.getName())
                .title(body.getTitle())
                .content(body.getContent())
                .price(body.getPrice())
                .brandName(body.getBrandName())
                .registeredAt(LocalDateTime.now())
                .partner(partnerRepository.getOne(body.getPartnerId()))
                .build();
        Item newItem = baseRepository.save(item);

        return response(newItem);
    }

    @Override
    public Header read(Long id) {
        
        return baseRepository.findById(id).map(item -> response(item))
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request){

        ItemApiRequest itemApiRequest = request.getData();
        Optional<Item> optional = baseRepository.findById(itemApiRequest.getId());

        return  optional.map( item -> {
            item.
                    setStatus(itemApiRequest.getStatus())
                    .setName(itemApiRequest.getName())
                    .setTitle(itemApiRequest.getTitle())
                    .setContent(itemApiRequest.getContent())
                    .setPrice(itemApiRequest.getPrice())
                    .setBrandName(itemApiRequest.getBrandName())
                    .setRegisteredAt(itemApiRequest.getRegisteredAt())
                    .setUnregisteredAt(itemApiRequest.getUnregisteredAt())
                    .setPartner(partnerRepository.getOne(itemApiRequest.getPartnerId()));
                    return item;
                    })
                .map(item -> baseRepository.save(item))
                .map(updateItem -> response(updateItem))
                .orElseGet( () -> Header.ERROR("데이터 없음") );
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map( item ->  {
                    baseRepository.delete(item);
                    return Header.OK();
                }).orElseGet(() -> Header.ERROR("데이터 없음 "));
    }

    public Header<ItemApiResponse> response(Item item){

        ItemApiResponse body = ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .registeredAt(item.getRegisteredAt())
                .partnerId(item.getPartner().getId())
                .build();
        return Header.OK(body);
    }
}
