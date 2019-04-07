package com.queuewise.demo.models;

public class Hour {




    private Integer shopId;
    private Integer day;
    private Integer hour;
    private Integer traffic;

    public Hour() {
    }

    public Integer getShopId() {
        return shopId;
    }

    public Hour setShopId(Integer shopId) {
        this.shopId = shopId;
        return this;
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
