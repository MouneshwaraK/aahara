package com.codebele.aahara.Models.Models;

public class OrderedItemsModel {
    String item_name,no_of_items,cost_per_each,total_amt;

    public OrderedItemsModel(String item_name, String no_of_items, String cost_per_each, String total_amt) {
        this.item_name = item_name;
        this.no_of_items = no_of_items;
        this.cost_per_each = cost_per_each;
        this.total_amt = total_amt;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getNo_of_items() {
        return no_of_items;
    }

    public void setNo_of_items(String no_of_items) {
        this.no_of_items = no_of_items;
    }

    public String getCost_per_each() {
        return cost_per_each;
    }

    public void setCost_per_each(String cost_per_each) {
        this.cost_per_each = cost_per_each;
    }

    public String getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(String total_amt) {
        this.total_amt = total_amt;
    }
}
