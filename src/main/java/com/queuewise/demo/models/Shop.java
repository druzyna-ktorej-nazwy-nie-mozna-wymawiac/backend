package com.queuewise.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shops")
public class Shop {

    @Id
    Integer id;

    String name;
    String addres;
    @Column(name = "opening_hour")
    String openingHour;

    @Column(name = "closing_hour")
    String closingHour;

    String traffic;
    String lat;
    String lng;

    private Shop() {

    }
}

