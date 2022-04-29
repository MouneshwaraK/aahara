package com.codebele.aahara.Models.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderHistoryPojo implements Serializable {


    @SerializedName("order")
    @Expose
    private List<Order> order = null;

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }


    public class Item {

        @SerializedName("sk_order_details")
        @Expose
        private String skOrderDetails;
        @SerializedName("restuarant_id")
        @Expose
        private String restuarantId;
        @SerializedName("sk_items_id")
        @Expose
        private String skItemsId;
        @SerializedName("item_name")
        @Expose
        private String itemName;
        @SerializedName("item_rating")
        @Expose
        private String itemRating;
        @SerializedName("item_type")
        @Expose
        private String itemType;
        @SerializedName("new_price")
        @Expose
        private String newPrice;
        @SerializedName("cart_count")
        @Expose
        private String cartCount;
        @SerializedName("cprice")
        @Expose
        private String cprice;

        @SerializedName("image")
        @Expose
        private String image;



        public String getSkOrderDetails() {
            return skOrderDetails;
        }

        public void setSkOrderDetails(String skOrderDetails) {
            this.skOrderDetails = skOrderDetails;
        }

        public String getRestuarantId() {
            return restuarantId;
        }

        public void setRestuarantId(String restuarantId) {
            this.restuarantId = restuarantId;
        }

        public String getSkItemsId() {
            return skItemsId;
        }

        public void setSkItemsId(String skItemsId) {
            this.skItemsId = skItemsId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemRating() {
            return itemRating;
        }

        public void setItemRating(String itemRating) {
            this.itemRating = itemRating;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getNewPrice() {
            return newPrice;
        }

        public void setNewPrice(String newPrice) {
            this.newPrice = newPrice;
        }

        public String getCartCount() {
            return cartCount;
        }

        public void setCartCount(String cartCount) {
            this.cartCount = cartCount;
        }

        public String getCprice() {
            return cprice;
        }

        public void setCprice(String cprice) {
            this.cprice = cprice;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }


    public class Order {

        @SerializedName("sk_order_id")
        @Expose
        private String skOrderId;
        @SerializedName("restuarant_id")
        @Expose
        private String restuarantId;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("total_order_value")
        @Expose
        private String totalOrderValue;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("created_time")
        @Expose
        private String createdTime;

        @SerializedName("city_name")
        @Expose
        private String city_name;

        @SerializedName("payment_status")
        @Expose
        private String payment_status;
        @SerializedName("order_status")
        @Expose
        private String order_status;
        @SerializedName("item")
        @Expose
        private List<Item> item = null;

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getSkOrderId() {
            return skOrderId;
        }

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        public void setSkOrderId(String skOrderId) {
            this.skOrderId = skOrderId;
        }

        public String getRestuarantId() {
            return restuarantId;
        }

        public void setRestuarantId(String restuarantId) {
            this.restuarantId = restuarantId;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public String getCity() {
            return city;
        }
        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }


        public void setCity(String city) {
            this.city = city;
        }

        public String getTotalOrderValue() {
            return totalOrderValue;
        }

        public void setTotalOrderValue(String totalOrderValue) {
            this.totalOrderValue = totalOrderValue;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public List<Item> getItem() {
            return item;
        }

        public void setItem(List<Item> item) {
            this.item = item;
        }

    }
}