package com.codebele.aahara.Models.Models;

public class MarketModel {
    String shop_name,location,items,time;

    public MarketModel(String shop_name, String location, String items, String time) {
        this.shop_name = shop_name;
        this.location = location;
        this.items = items;
        this.time = time;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
