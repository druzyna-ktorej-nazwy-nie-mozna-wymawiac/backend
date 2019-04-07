package com.queuewise.demo.models;

import java.util.List;

public class ScrapedDataResponse {

    private List<SingleHour> data;

    public ScrapedDataResponse() {
    }

    public List<SingleHour> getData() {
        return data;
    }

    public void setData(List<SingleHour> data) {
        this.data = data;
    }
}
