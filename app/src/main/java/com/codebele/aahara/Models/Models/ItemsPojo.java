package com.codebele.aahara.Models.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemsPojo {

    @SerializedName("categoryitem_id")
    @Expose
    private String categoryitemId;
    @SerializedName("Items_categoryname")
    @Expose
    private String itemsCategoryname;
    @SerializedName("item")
    @Expose
    private List<Item> items = null;

    public ItemsPojo(String categoryitemId, String itemsCategoryname, List<Item> items) {
        super();
        this.categoryitemId = categoryitemId;
        this.itemsCategoryname = itemsCategoryname;
        this.items = items;
    }

    public String getCategoryitemId() {
        return categoryitemId;
    }

    public void setCategoryitemId(String categoryitemId) {
        this.categoryitemId = categoryitemId;
    }

    public String getItemsCategoryname() {
        return itemsCategoryname;
    }

    public void setItemsCategoryname(String itemsCategoryname) {
        this.itemsCategoryname = itemsCategoryname;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    public class Item {

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
        @SerializedName("medium_price")
        @Expose
        private String mediumPrice;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("itemCount")
        @Expose
        private Integer itemCount;
        @SerializedName("offer_price")
        @Expose
        private String offerPrice;
        @SerializedName("actual_price")
        @Expose
        private String actualPrice;

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

        public String getMediumPrice() {
            return mediumPrice;
        }

        public void setMediumPrice(String mediumPrice) {
            this.mediumPrice = mediumPrice;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getItemCount() {
            return itemCount;
        }

        public void setItemCount(Integer itemCount) {
            this.itemCount = itemCount;
        }

        public String getOfferPrice() {
            return offerPrice;
        }

        public void setOfferPrice(String offerPrice) {
            this.offerPrice = offerPrice;
        }

        public String getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(String actualPrice) {
            this.actualPrice = actualPrice;
        }
    }
}