package com.codebele.aahara.Models.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class HomeModel {
    @SerializedName("selected")
    @Expose
    private boolean isSelected =false;


    @SerializedName("section_id")
    @Expose
    private String sectionId;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("vendor")
    @Expose
    private List<Vendor> vendor = null;

    public HomeModel(String sectionId, String sectionName, List<Vendor> vendor) {
        super();
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.vendor = vendor;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<Vendor> getVendor() {
        return vendor;
    }

    public void setVendor(List<Vendor> vendor) {
        this.vendor = vendor;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public class Timeing {

        @SerializedName("sk_timing_id")
        @Expose
        private String skTimingId;
        @SerializedName("Day")
        @Expose
        private String day;
        @SerializedName("t1_ot")
        @Expose
        private String t1Ot;
        @SerializedName("t1_ct")
        @Expose
        private String t1Ct;
        @SerializedName("t2_ot")
        @Expose
        private String t2Ot;
        @SerializedName("t2_ct")
        @Expose
        private String t2Ct;
        @SerializedName("t3_ot")
        @Expose
        private String t3Ot;
        @SerializedName("t3_ct")
        @Expose
        private String t3Ct;
        @SerializedName("timing_status")
        @Expose
        private String timingStatus;
        @SerializedName("rest_id")
        @Expose
        private String restId;

        public Timeing(String skTimingId, String day, String t1Ot, String t1Ct, String t2Ot, String t2Ct, String t3Ot, String t3Ct, String timingStatus, String restId) {
            super();
            this.skTimingId = skTimingId;
            this.day = day;
            this.t1Ot = t1Ot;
            this.t1Ct = t1Ct;
            this.t2Ot = t2Ot;
            this.t2Ct = t2Ct;
            this.t3Ot = t3Ot;
            this.t3Ct = t3Ct;
            this.timingStatus = timingStatus;
            this.restId = restId;
        }

        public String getSkTimingId() {
            return skTimingId;
        }

        public void setSkTimingId(String skTimingId) {
            this.skTimingId = skTimingId;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getT1Ot() {
            return t1Ot;
        }

        public void setT1Ot(String t1Ot) {
            this.t1Ot = t1Ot;
        }

        public String getT1Ct() {
            return t1Ct;
        }

        public void setT1Ct(String t1Ct) {
            this.t1Ct = t1Ct;
        }

        public String getT2Ot() {
            return t2Ot;
        }

        public void setT2Ot(String t2Ot) {
            this.t2Ot = t2Ot;
        }

        public String getT2Ct() {
            return t2Ct;
        }

        public void setT2Ct(String t2Ct) {
            this.t2Ct = t2Ct;
        }

        public String getT3Ot() {
            return t3Ot;
        }

        public void setT3Ot(String t3Ot) {
            this.t3Ot = t3Ot;
        }

        public String getT3Ct() {
            return t3Ct;
        }

        public void setT3Ct(String t3Ct) {
            this.t3Ct = t3Ct;
        }

        public String getTimingStatus() {
            return timingStatus;
        }

        public void setTimingStatus(String timingStatus) {
            this.timingStatus = timingStatus;
        }

        public String getRestId() {
            return restId;
        }

        public void setRestId(String restId) {
            this.restId = restId;
        }

    }

    public class Vendor {

        @SerializedName("rest_status")
        @Expose
        private String restStatus;

        @SerializedName("open")
        @Expose
        private String open;
        @SerializedName("close")
        @Expose
        private String close;


        @SerializedName("sk_restaurant_id")
        @Expose
        private String skRestaurantId;
        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("store_type")
        @Expose
        private String storeType;
        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("ApproxPerson_Cost")
        @Expose
        private String approxPersonCost;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("timing")
        @Expose
        private List<Timeing> timeings = null;

        public Vendor(String restStatus,String skRestaurantId, String restaurantName, String storeType, String rating, String approxPersonCost, List<Timeing> timeings,String open,String close) {
            super();
            this.restStatus = restStatus;
            this.skRestaurantId = skRestaurantId;
            this.restaurantName = restaurantName;
            this.storeType = storeType;
            this.rating = rating;
            this.approxPersonCost = approxPersonCost;
            this.timeings = timeings;
            this.open = open;
            this.close = close;
        }

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }

        public String getRestStatus() {
            return restStatus;
        }

        public void setRestStatus(String restStatus) {
            this.restStatus = restStatus;
        }

        public String getSkRestaurantId() {
            return skRestaurantId;
        }

        public void setSkRestaurantId(String skRestaurantId) {
            this.skRestaurantId = skRestaurantId;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public String getStoreType() {
            return storeType;
        }

        public void setStoreType(String storeType) {
            this.storeType = storeType;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getApproxPersonCost() {
            return approxPersonCost;
        }

        public void setApproxPersonCost(String approxPersonCost) {
            this.approxPersonCost = approxPersonCost;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public List<Timeing> getTimeings() {
            return timeings;
        }

        public void setTimeings(List<Timeing> timeings) {
            this.timeings = timeings;
        }

    }
}
