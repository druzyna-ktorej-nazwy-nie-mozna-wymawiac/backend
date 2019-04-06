package com.queuewise.demo.controllers;

import com.queuewise.demo.models.Shop;
import com.queuewise.demo.services.ShopsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/shop")
public class ShopsController {


    private ShopsService shopsService;

    public ShopsController(ShopsService shopsService) {
        this.shopsService = shopsService;
    }

    @GetMapping
    public String getShopAddressById(@RequestParam("id") int id) {
        Shop shop = shopsService.getShop(id);

        return shop.getAddress();


    }

}
