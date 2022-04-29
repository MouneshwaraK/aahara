package com.codebele.aahara.Models.Models;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ViewCartPojo implements Serializable {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Items> data = null;
    @SerializedName("totalCost")
    @Expose
    private String totalCost;
    @SerializedName("totalDeliveryCharges")
    @Expose
    private String totalDeliveryCharges;
    @SerializedName("totalTaxAmount")
    @Expose
    private String totalTaxAmount;
    @SerializedName("totalPayableAmount")
    @Expose
    private String totalPayableAmount;
    @SerializedName("taxPercentage")
    @Expose
    private String taxPercentage;
    @SerializedName("couponAmount")
    @Expose
    private String coupon;
    @SerializedName("restuarants_id")
    @Expose
    private String restaurant_id;
    @SerializedName("coupon_code")
    @Expose
    private String couponcode;

    public String getCouponcode() {
        return couponcode;
    }

    public void setCouponcode(String couponcode) {
        this.couponcode = couponcode;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public List<Items> getData() {
        return data;
    }

    public String getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(String taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Items> getItems() {
        return data;
    }

    public void setData(List<Items> data) {
        this.data = data;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getTotalDeliveryCharges() {
        return totalDeliveryCharges;
    }

    public void setTotalDeliveryCharges(String totalDeliveryCharges) {
        this.totalDeliveryCharges = totalDeliveryCharges;
    }

    public String getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(String totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public String getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(String totalPayableAmount) {
        this.totalPayableAmount = totalPayableAmount;
    }

    public class Items {

        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("sk_cart_id")
        @Expose
        private String skCartId;
        @SerializedName("sk_items_id")
        @Expose
        private String skItemsId;
        @SerializedName("item_name")
        @Expose
        private String itemName;
        @SerializedName("full_price")
        @Expose
        private String fullPrice;
        @SerializedName("cart_count")
        @Expose
        private String cartCount;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("Delivery_Charges")
        @Expose
        private String deliveryCharges;


        @SerializedName("min_cart")
        @Expose
        private String minimumCartAmount;

        @SerializedName("tax_amount")
        @Expose
        private String taxAmount;
        @SerializedName("actual_price")
        @Expose
        private String actualPrice;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSkCartId() {
            return skCartId;
        }

        public void setSkCartId(String skCartId) {
            this.skCartId = skCartId;
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

        public String getFullPrice() {
            return fullPrice;
        }

        public void setFullPrice(String fullPrice) {
            this.fullPrice = fullPrice;
        }

        public String getCartCount() {
            return cartCount;
        }

        public void setCartCount(String cartCount) {
            this.cartCount = cartCount;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDeliveryCharges() {
            return deliveryCharges;
        }

        public void setDeliveryCharges(String deliveryCharges) {
            this.deliveryCharges = deliveryCharges;
        }

        public String getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(String taxAmount) {
            this.taxAmount = taxAmount;
        }

        public String getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(String actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getMinimumCartAmount() {
            return minimumCartAmount;
        }

        public void setMinimumCartAmount(String minimumCartAmount) {
            this.minimumCartAmount = minimumCartAmount;
        }
    }
}

