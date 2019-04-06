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

    public double getDistanceWithLatAndLng(String lat1 , String lng1, String lat2, String lng2){
        double R = 6371; // metres

        double latD1 = Double.parseDouble(lat1);
        double latD2 = Double.parseDouble(lat2);
        double lngD1 = Double.parseDouble(lng1);
        double lngD2 = Double.parseDouble(lng2);

        double latRad1 = Math.toRadians(latD1);
        double latRad2 = Math.toRadians(latD2);


        double diffLatRad = Math.toRadians(latD2-latD1);
        double diffLngRad = Math.toRadians(lngD2-lngD1);
        double a = Math.sin(diffLatRad/2)*Math.sin(diffLatRad/2) + Math.cos(latRad1)*Math.cos(latRad2)*Math.sin(diffLngRad/2)*Math.cos(diffLngRad/2);
        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        double distance = R * c ;

        return  distance;

    }
    public double getDistanceWithLatAndLng(String lat1 , String lng1, Shop shop){
        return  getDistanceWithLatAndLng(lat1,lng1,shop.getLat(),shop.getLng());

    }

//    public Shop getNearestShop(String deviceLat, String deviceLng){
//        return shopRepository.
//
//    }

}
