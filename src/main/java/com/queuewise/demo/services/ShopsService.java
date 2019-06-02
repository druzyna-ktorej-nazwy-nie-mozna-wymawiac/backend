package com.queuewise.demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.queuewise.demo.models.*;
import com.queuewise.demo.repositories.ShopRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    public Shop getNearestShop(int deviceHour, int deviceDay, double deviceLat, double deviceLng){

        Optional<Shop> optionalShop = shopRepository.findAll().stream()
                .filter(shop -> LocalTime.parse(shop.getClosingHour()).getHour() >= deviceHour)
                .filter(shop -> LocalTime.parse(shop.getOpeningHour()).getHour() <= deviceHour)
                .filter(shop -> deviceDay != 7 || shop.isOpenOnSundays())
                .min(Comparator.comparing(shop -> getDistanceWithLatAndLng(deviceLat, deviceLng, shop)));

        if(optionalShop.isPresent()) {

            if(deviceDay != 7 || optionalShop.get().isOpenOnSundays()) {
                return optionalShop.get();
            }
        }

        return null;

    }

    public ShopResponseField getNearestShopResponse(int deviceHour, int deviceDay, double deviceLat, double deviceLng){
        Shop shop = getNearestShop(deviceHour, deviceDay,deviceLat,deviceLng);

//        if (deviceDay == 7 && !shop.isOpenOnSundays()) {
//            shop = null;
//        }

        if(shop!=null){
            double distance = getDistanceWithLatAndLng(deviceLat, deviceLng, shop.getLat(), shop.getLng());
            Hour hour = getBestTrafficHourForShop(shop,deviceHour);
            if(hour!=null){
                return new ShopResponseField(getNearestShop(deviceHour, deviceDay,deviceLat,deviceLng),hour.getHour(),hour.getTraffic(), distance);
            }
        }
        return new ShopResponseField(shop);
    }

//    public Shop getBestOverallShop(int deviceHour, double deviceLat, double deviceLng){
//
//        return new Shop();
//
//    }



    public Shop getBestShopNow(int deviceHour,int deviceDay,  double deviceLat, double deviceLng){

        List<Shop> shopList = getShopsInYKm(deviceLat, deviceLng,5000);

        List<Hour> allHours = new ArrayList<>();

        for (Shop s : shopList) {
            ShopTrafficData trafficData = s.getTrafficData();
            System.out.println(deviceDay);
            if(deviceDay==7 && !s.isOpenOnSundays())
                continue;

             allHours.addAll(trafficData.getData().stream().map((hour -> hour.setShopId(s.getId())))
                     .filter(hour -> deviceHour >= LocalTime.parse(s.getOpeningHour()).getHour())
                     .filter(hour -> deviceHour<= LocalTime.parse(s.getClosingHour()).getHour())
                     .filter(hour -> hour.getDay() == deviceDay)
                     .filter(hour -> deviceDay != 7 || s.isOpenOnSundays())
                     .collect(Collectors.toList()));
        }


        Optional<Hour> bestHourOpt = allHours.stream().filter(hour -> hour.getHour() == deviceHour)
                .min(Comparator.comparing(hour -> hour.getTraffic()));

        if(bestHourOpt.isPresent()) {

            shopList.stream().filter(shop -> shop.getId().equals(bestHourOpt.get().getShopId())).collect(Collectors.toList());
        } else {
            return null;
        }


        if (shopList.size()>0)
            return shopList.get(0);
        return null;

    }

    public ShopResponseField getBestShopNowResponse(int deviceHour, int deviceDay, double deviceLat, double deviceLng){
        Shop shop = getBestShopNow(deviceHour,deviceDay,deviceLat,deviceLng);




        if (shop!=null)
        {
            System.out.println(shop.getName());
            double distance = getDistanceWithLatAndLng(deviceLat, deviceLng, shop.getLat(), shop.getLng());
            return new ShopResponseField(shop,deviceHour,getCurrentTraficByShopId(deviceHour,shop.getId()), distance);
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

    public List<Shop> getShopsInYKm(double deviceLat, double deviceLng, int Y){
        return shopRepository.findAll().stream().filter(shop -> getDistanceWithLatAndLng(deviceLat,deviceLng,shop)<Y).collect(Collectors.toList());
    }

    public Hour getBestTrafficHourForShop(Shop shop, int deviceHour){
        try {
            ShopTrafficData trafficData = mapper.readValue(shop.getData(), ShopTrafficData.class);

            Hour bestHour = trafficData.getData().stream()
                    .filter(hour -> hour.getHour()>=LocalTime.parse(shop.getOpeningHour()).getHour())
                    .filter(hour -> hour.getHour()<=LocalTime.parse(shop.getClosingHour()).getHour())
                    .filter(hour -> hour.getHour()>deviceHour)
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


    public BestShopResponse getResponse(String rawDateTime, double lat, double lng) {
        ZonedDateTime dateTime = ZonedDateTime.parse(rawDateTime);


        BestShopResponse response = new BestShopResponse();
        response.setNearest(getNearestShopResponse(dateTime.getHour(),dateTime.getDayOfWeek().getValue(),lat,lng));
        response.setBestNow(getBestShopNowResponse(dateTime.getHour(),dateTime.getDayOfWeek().getValue(),lat,lng));


        return response;
    }




}
