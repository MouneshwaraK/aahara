package com.codebele.aahara.ResponsePojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderSummaryPojo implements Serializable {
    @SerializedName("restuarant_id")
    @Expose
    private String restuarantId;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("restaurant_address")
    @Expose
    private String restaurantAddress;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("restaurant_mobile")
    @Expose
    private String restaurantMobile;
    @SerializedName("sk_order_id")
    @Expose
    private String skOrderId;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("ordered_date")
    @Expose
    private String orderedDate;
    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("address_name")
    @Expose
    private String addressName;
    @SerializedName("item")
    @Expose
    private List<Item> item = null;
    @SerializedName("tax_amount")
    @Expose
    private String taxAmount;
    @SerializedName("totalCost")
    @Expose
    private String totalCost;
    @SerializedName("totalTaxAmount")
    @Expose
    private String totalTaxAmount;
    @SerializedName("Delivery_Charges")
    @Expose
    private String deliveryCharges;
    @SerializedName("totalPayableAmount")
    @Expose
    private String totalPayableAmount;

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

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRestaurantMobile() {
        return restaurantMobile;
    }

    public void setRestaurantMobile(String restaurantMobile) {
        this.restaurantMobile = restaurantMobile;
    }

    public String getSkOrderId() {
        return skOrderId;
    }

    public void setSkOrderId(String skOrderId) {
        this.skOrderId = skOrderId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(String totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(String totalPayableAmount) {
        this.totalPayableAmount = totalPayableAmount;
    }
    public class Item {

        @SerializedName("sk_order_details")
        @Expose
        private String skOrderDetails;
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
        private Integer cprice;

        public String getSkOrderDetails() {
            return skOrderDetails;
        }

        public void setSkOrderDetails(String skOrderDetails) {
            this.skOrderDetails = skOrderDetails;
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

        public Integer getCprice() {
            return cprice;
        }

        public void setCprice(Integer cprice) {
            this.cprice = cprice;
        }

    }

}
