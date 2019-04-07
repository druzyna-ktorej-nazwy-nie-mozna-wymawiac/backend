package com.queuewise.demo.models;

import java.util.List;

public class ShopTrafficData {

    private List<Hour> data;

    public ShopTrafficData() {
    }

    public List<Hour> getData() {
        return data;
    }

    public void setData(List<Hour> data) {
        this.data = data;
    }
}
