package com.queuewise.demo.services;

import com.queuewise.demo.models.Shop;
import com.queuewise.demo.repositories.HoursRepository;
import com.queuewise.demo.repositories.ShopRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;

@Service
public class DataGenerationService {


    private ShopRepository shopRepository;
    private HoursRepository hoursRepository;

    private Random rng = new Random(123);

    public DataGenerationService(ShopRepository shopRepository, HoursRepository hoursRepository) {
        this.shopRepository = shopRepository;
        this.hoursRepository = hoursRepository;
    }


    public String generateRandomData() {

        List<Shop> shopList = shopRepository.findAll();
        hoursRepository.deleteAll();


        for(Shop s : shopList) {


            ZonedDateTime openingHour = ZonedDateTime.parse(s.getOpeningHour());
            ZonedDateTime closingHour = ZonedDateTime.parse(s.getClosingHour());


            for (int d = 0; d < 7; d++) {

                for (int h = 0; h < 24; h++) {


                    if(d < 6 || s.isOpenOnSundays()) {

                        if((h >= openingHour.getHour()) && (h <= closingHour.getHour())) {

                        int randomTraffic;
                            randomTraffic = rng.nextInt(100);


                        }

                    } else {

                    }




                }


            }




        }



        return "OK";
    }


}
