package com.queuewise.demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queuewise.demo.models.*;
import com.queuewise.demo.repositories.ShopRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopsService {

    private ShopRepository shopRepository;

    private static ObjectMapper mapper = new ObjectMapper();


    public ShopsService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }


    public Shop getShop(int id) {

        return shopRepository.getOne(id);

    }

    public double getDistanceWithLatAndLng(double lat1 , double lng1, double lat2, double lng2){
        double R = 6371; // metres

        double latRad1 = Math.toRadians(lat1);
        double latRad2 = Math.toRadians(lat2);


        double diffLatRad = Math.toRadians(lat2-lat1);
        double diffLngRad = Math.toRadians(lng2-lng1);
        double a = Math.sin(diffLatRad/2)*Math.sin(diffLatRad/2) + Math.cos(latRad1)*Math.cos(latRad2)*Math.sin(diffLngRad/2)*Math.cos(diffLngRad/2);
        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        double distance = R * c ;

        return  distance;

    }
    public double getDistanceWithLatAndLng(double lat1 , double lng1, Shop shop){
        return  getDistanceWithLatAndLng(lat1,lng1,shop.getLat(),shop.getLng());

    }

    public Shop getNearestShop(int deviceHour, double deviceLat, double deviceLng){
        return shopRepository.findAll().stream()
                .filter(shop -> LocalTime.parse(shop.getClosingHour()).getHour()>=deviceHour)
                .filter(shop -> LocalTime.parse(shop.getOpeningHour()).getHour()<=deviceHour)
                .min(Comparator.comparing(shop -> getDistanceWithLatAndLng(deviceLat,deviceLng,shop))).get();
    }

    public ShopResponseField getNearestShopResponse(int deviceHour, double deviceLat, double deviceLng){
        Shop shop = getNearestShop(deviceHour,deviceLat,deviceLng);
        if(shop!=null){
            Hour hour = getBestTrafficHourForShop(shop);
            if(hour!=null){
                return new ShopResponseField(getNearestShop(deviceHour,deviceLat,deviceLng),hour.getHour(),hour.getTraffic());
            }
        }
        return new ShopResponseField(shop);
    }


    public Shop getBestShopNow(int deviceHour, double deviceLat, double deviceLng){

        List<Shop> shopList = getShopsIn5Km(deviceLat, deviceLng);

        List<Hour> allHours = new ArrayList<>();

        for (Shop s : shopList) {
            ShopTrafficData trafficData = s.getTrafficData();

             allHours.addAll(trafficData.getData().stream().map((hour -> hour.setShopId(s.getId()))).collect(Collectors.toList()));
        }


        Hour betHour = allHours.stream().filter(hour -> hour.getHour() == deviceHour)
                .min(Comparator.comparing(hour -> hour.getTraffic())).get();

        shopList.stream().filter(shop -> shop.getId().equals(betHour.getShopId())).collect(Collectors.toList());

        if (shopList.size()>0)
            return shopList.get(0);
        return null;

    }

    public ShopResponseField getBestShopNowResponse(int deviceHour, double deviceLat, double deviceLng){
        Shop shop = getBestShopNow(deviceHour,deviceLat,deviceLng);
        shop.setData("");
        if (shop!=null)
        {
            return new ShopResponseField(shop,deviceHour,getCurrentTraficByShopId(deviceHour,shop.getId()));
        }
        return null;

    }

    public Integer getCurrentTraficByShopId(int deviceHour, int shopId){


        try {
            ShopTrafficData trafficData = mapper.readValue(shopRepository.getOne(shopId).getData(), ShopTrafficData.class);
            return trafficData.getData().stream().filter(hour -> hour.getHour()==deviceHour).collect(Collectors.toList()).get(0).getTraffic();

        } catch (IOException e) {
                    e.printStackTrace();
                    return null;
        }



//        return hoursRepository.findAll().stream().filter(hour -> hour.getShopId()==shopId).filter(hour -> hour.getHour()==deviceHour).collect(Collectors.toList()).get(0).getTraffic();
    }

    public List<Shop> getShopsIn5Km(double deviceLat, double deviceLng){
        return shopRepository.findAll().stream().filter(shop -> getDistanceWithLatAndLng(deviceLat,deviceLng,shop)<50000).collect(Collectors.toList());
    }

    public Hour getBestTrafficHourForShop(Shop shop){
        try {
            ShopTrafficData trafficData = mapper.readValue(shop.getData(), ShopTrafficData.class);

            Hour bestHour = trafficData.getData().stream()
                    .filter(hour -> hour.getHour()>=LocalTime.parse(shop.getOpeningHour()).getHour())
                    .filter(hour -> hour.getHour()<=LocalTime.parse(shop.getClosingHour()).getHour())
                    .min(Comparator.comparing(hour -> hour.getTraffic())).get();


            Hour toRetrun = new Hour();
            toRetrun.setShopId(shop.getId());
            toRetrun.setDay(bestHour.getDay());
            toRetrun.setHour(bestHour.getHour());
            toRetrun.setTraffic(bestHour.getTraffic());

            return toRetrun;

        } catch (IOException e) {
            e.printStackTrace();

        }

        return null;

    }




}
