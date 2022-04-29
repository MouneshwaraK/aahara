package com.codebele.aahara.Models.Models;

public class HistoryModel {
    String restaurants_name,location,items,date,total_amt,rating,status;

    public HistoryModel(String restaurants_name, String location, String items, String date, String total_amt, String rating, String status) {
        this.restaurants_name = restaurants_name;
        this.location = location;
        this.items = items;
        this.date = date;
        this.total_amt = total_amt;
        this.rating = rating;
        this.status = status;
    }

    public String getRestaurants_name() {
        return restaurants_name;
    }

    public void setRestaurants_name(String restaurants_name) {
        this.restaurants_name = restaurants_name;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(String total_amt) {
        this.total_amt = total_amt;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
