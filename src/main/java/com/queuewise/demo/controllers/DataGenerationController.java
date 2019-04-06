package com.queuewise.demo.controllers;

import com.queuewise.demo.models.Shop;
import com.queuewise.demo.repositories.HoursRepository;
import com.queuewise.demo.repositories.ShopRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController("/generate")
public class DataGenerationController {

    private ShopRepository shopRepository;
    private HoursRepository hoursRepository;

    private Random rng = new Random(123);

    public DataGenerationController(ShopRepository shopRepository, HoursRepository hoursRepository) {
        this.shopRepository = shopRepository;
        this.hoursRepository = hoursRepository;
    }

    @PostMapping("/")
    public String generateNewData() {

        List<Shop> shopList = shopRepository.findAll();
        hoursRepository.deleteAll();


        for(Shop s : shopList) {

            boolean isOpenAtNight = rng.nextDouble() > 0.85;

            for (int d = 0; d < 7; d++) {


                for (int h = 0; h < 24; h++) {




                }


            }




        }



        return "OK";
    }





}
