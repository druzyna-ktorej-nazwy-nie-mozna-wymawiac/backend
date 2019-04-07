package com.queuewise.demo.controllers;

import com.queuewise.demo.models.BestShopResponse;
import com.queuewise.demo.models.Shop;
import com.queuewise.demo.models.ShopResponseField;
import com.queuewise.demo.services.ShopsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController()
@RequestMapping("/api")
public class ApiController {


    private ShopsService shopsService;

    public ApiController(ShopsService shopsService) {
        this.shopsService = shopsService;
    }

    @GetMapping("/best_shop")
    public BestShopResponse getBestShopChoice(@RequestParam("date_time") String rawDateTime, @RequestParam("lat") double lat, @RequestParam("lng") double lng ) {
        ZonedDateTime dateTime = ZonedDateTime.parse(rawDateTime);


        BestShopResponse response = new BestShopResponse();
        response.setBestNearest(shopsService.getNearestShopResponse(dateTime.getHour(),lat,lng));
        response.setBestNow(shopsService.getBestShopNowResponse(dateTime.getHour(),lat,lng));

        return response;
    }











}
