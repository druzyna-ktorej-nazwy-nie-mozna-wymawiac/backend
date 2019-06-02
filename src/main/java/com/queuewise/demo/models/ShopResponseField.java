package com.queuewise.demo.models;

public class ShopResponseField {

    private Shop shop;
    private Integer recommendedHour;
    private Integer bestTraffic;
    private Double distance;

    public ShopResponseField(Shop shop) {
        this.shop = shop;
    }

    public ShopResponseField(Shop shop, Integer recommendedHour, Integer bestTraffic, Double distance) {
        this.shop = shop;
        this.recommendedHour = recommendedHour;
        this.bestTraffic = bestTraffic;
        this.distance = distance;
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

    public Integer getPredictedWaitTimeInSeconds() {
        if(bestTraffic == null) {
            return null;
        }
        return (int) (30 + bestTraffic * 0.3);
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
