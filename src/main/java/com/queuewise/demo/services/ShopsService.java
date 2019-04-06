package com.queuewise.demo.services;

import com.queuewise.demo.models.Shop;
import com.queuewise.demo.repositories.ShopRepository;
import org.springframework.stereotype.Service;

@Service
public class ShopsService {

    private ShopRepository shopRepository;

    public ShopsService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }


    public Shop getShop(int id) {

        return shopRepository.getOne(id);

    }

}
