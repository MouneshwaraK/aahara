package com.codebele.aahara.Models.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HotelModel implements Serializable {

    @SerializedName("menuList")
    @Expose
    private List<MenuList> menuList = null;

    public HotelModel(List<MenuList> menuList) {
        this.menuList = menuList;
    }

    public List<MenuList> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuList> menuList) {
        this.menuList = menuList;
    }
    public static class MenuItem {

        @SerializedName("FoodName")
        @Expose
        private String foodName;
        @SerializedName("HotelName")
        @Expose
        private String hotelName;
        @SerializedName("Spicy")
        @Expose
        private String spicy;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("veg")
        @Expose
        private String veg;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("isadded")
        @Expose
        private boolean isadded=false;

        public MenuItem(String foodName, String hotelName, String spicy, String amount, String veg, String quantity) {
            super();
            this.foodName = foodName;
            this.hotelName = hotelName;
            this.spicy = spicy;
            this.amount = amount;
            this.veg = veg;
            this.quantity = quantity;
//            this.normal = normal;
        }
        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }

        public String getSpicy() {
            return spicy;
        }

        public void setSpicy(String spicy) {
            this.spicy = spicy;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getVeg() {
            return veg;
        }

        public void setVeg(String veg) {
            this.veg = veg;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

//        public String getNormal() {
//            return normal;
//        }
//
//        public void setNormal(String normal) {
//            this.normal = normal;
//        }
    }

    public static class MenuList implements Serializable {

        @SerializedName("menuType")
        @Expose
        private String menuType;
        @SerializedName("menuItems")
        @Expose
        private List<MenuItem> menuItems = null;

        public MenuList(String menuType, List<MenuItem> menuItems) {
            super();
            this.menuType = menuType;
            this.menuItems = menuItems;
        }

        public String getMenuType() {
            return menuType;
        }

        public void setMenuType(String menuType) {
            this.menuType = menuType;
        }

        public List<MenuItem> getMenuItems() {
            return menuItems;
        }

        public void setMenuItems(List<MenuItem> menuItems) {
            this.menuItems = menuItems;
        }

    }
}












