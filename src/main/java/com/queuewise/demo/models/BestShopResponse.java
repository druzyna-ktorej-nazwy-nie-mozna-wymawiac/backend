package com.queuewise.demo.models;

public class BestShopResponse {

    private ShopResponseField bestNearest;
    private ShopResponseField bestNow;
    private ShopResponseField bestOverall;


    public BestShopResponse() {
    }


    public ShopResponseField getBestNearest() {
        return bestNearest;
    }

    public void setNearest(ShopResponseField bestNearest) {
        this.bestNearest = bestNearest;
    }

    public ShopResponseField getBestNow() {
        return bestNow;
    }

    public void setBestNow(ShopResponseField bestNow) {
        this.bestNow = bestNow;
    }

    public ShopResponseField getBestOverall() {
        return bestOverall;
    }

    public void setBestOverall(ShopResponseField bestOverall) {
        this.bestOverall = bestOverall;
    }
}
