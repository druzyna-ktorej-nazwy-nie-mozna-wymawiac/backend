package com.queuewise.demo.models;

public class BestShopResponse {

    private Shop bestByDistance;
    private Shop bestByTime;
    private Shop bestOverall;

    public BestShopResponse() {
    }

    public Shop getBestByDistance() {
        return bestByDistance;
    }

    public void setBestByDistance(Shop bestByDistance) {
        this.bestByDistance = bestByDistance;
    }

    public Shop getBestByTime() {
        return bestByTime;
    }

    public void setBestByTime(Shop bestByTime) {
        this.bestByTime = bestByTime;
    }

    public Shop getBestOverall() {
        return bestOverall;
    }

    public void setBestOverall(Shop bestOverall) {
        this.bestOverall = bestOverall;
    }
}
