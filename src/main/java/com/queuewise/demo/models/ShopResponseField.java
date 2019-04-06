package com.queuewise.demo.models;

public class ShopResponseField {

    private Shop shop;
    private Integer recommendedHour;
    private Integer bestTraffic;

    public ShopResponseField(Shop shop) {
        this.shop = shop;
    }

    public ShopResponseField(Shop shop, Integer recommendedHour, Integer bestTraffic) {
        this.shop = shop;
        this.recommendedHour = recommendedHour;
        this.bestTraffic = bestTraffic;
    }


    public Shop getShop() {
        return shop;
        
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Integer getRecommendedHour() {
        return recommendedHour;
    }

    public void setRecommendedHour(Integer recommendedHour) {
        this.recommendedHour = recommendedHour;
    }

    public Integer getBestTraffic() {
        return bestTraffic;
    }

    public void setBestTraffic(Integer bestTraffic) {
        this.bestTraffic = bestTraffic;
    }
}
