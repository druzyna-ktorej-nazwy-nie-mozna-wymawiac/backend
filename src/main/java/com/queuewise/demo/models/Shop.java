package com.queuewise.demo.models;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.IOException;

@Entity
@Table(name = "shops")
public class Shop {

    @Id
    private Integer id;

    private String name;
    private String address;
    @Column(name = "opening_hour")
    private String openingHour;

    @Column(name = "closing_hour")
    private String closingHour;

    private double lat;
    private double lng;

    @Column(name = "open_sundays")
    private Boolean openOnSundays;

    private String query;
    private String data;

    Shop() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    public String getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(String closingHour) {
        this.closingHour = closingHour;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Boolean isOpenOnSundays() {
        return openOnSundays;
    }

    public void setOpenOnSundays(Boolean openOnSundays) {
        this.openOnSundays = openOnSundays;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ShopTrafficData getTrafficData() {

        if (getData() == null || getData().equals("")) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            ShopTrafficData trafficData = mapper.readValue(getData(), ShopTrafficData.class);
            return trafficData;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}

