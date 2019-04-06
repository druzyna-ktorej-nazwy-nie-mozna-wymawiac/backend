package com.queuewise.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "hours")
public class Hour {

    @Id
    private Integer id;

    @Column(name = "shop_id")
    private
    Integer shopId;
    private Integer day;
    private Integer hour;
    private Integer traffic;

    public Hour() {
    }

    public Hour(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getTraffic() {
        return traffic;
    }

    public void setTraffic(Integer traffic) {
        this.traffic = traffic;
    }
}
