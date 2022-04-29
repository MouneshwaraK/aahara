package com.codebele.aahara.Models.Models;

public class CartModel {
    String Image,item_name,price;

    public CartModel(String image, String item_name, String price) {
        Image = image;
        this.item_name = item_name;
        this.price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
