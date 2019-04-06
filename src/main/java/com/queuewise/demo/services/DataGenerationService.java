package com.queuewise.demo.services;

import com.queuewise.demo.models.Hour;
import com.queuewise.demo.models.Shop;
import com.queuewise.demo.repositories.HoursRepository;
import com.queuewise.demo.repositories.ShopRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
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

        List<Hour> hourList = new ArrayList<>(shopList.size() * 24 * 7);

        int i = 0;


        for(Shop s : shopList) {


            LocalTime openingHour = LocalTime.parse(s.getOpeningHour());
            LocalTime closingHour = LocalTime.parse(s.getClosingHour());


            for (int d = 0; d < 7; d++) {

                for (int h = 0; h < 24; h++) {
                    int randomTraffic = 0;

                    if(d < 6 || ( s.isOpenOnSundays() != null && s.isOpenOnSundays())) {

                        if((h >= openingHour.getHour()) && (h <= closingHour.getHour())) {

                            randomTraffic = rng.nextInt(100);
                        }
                    }

                    // TODO: 06.04.19 auto generate id
                    Hour newEntry = new Hour(i);
                    newEntry.setShopId(s.getId());
                    newEntry.setDay(d);
                    newEntry.setHour(h);
                    newEntry.setTraffic(randomTraffic);

                    i++;
                    hourList.add(newEntry);

                }


            }




        }

        hoursRepository.saveAll(hourList);


        return "OK";
    }


}
