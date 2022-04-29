package com.codebele.aahara.ResponsePojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpDateAddressPojo {

        @SerializedName("address_name")
        @Expose
        private String addressName;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("category_adress")
        @Expose
        private String categoryAdress;
        @SerializedName("landmark")
        @Expose
        private String landmark;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("address_status")
        @Expose
        private String addressStatus;

        public String getAddressName() {
            return addressName;
        }

        public void setAddressName(String addressName) {
            this.addressName = addressName;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCategoryAdress() {
            return categoryAdress;
        }

        public void setCategoryAdress(String categoryAdress) {
            this.categoryAdress = categoryAdress;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getAddressStatus() {
            return addressStatus;
        }

        public void setAddressStatus(String addressStatus) {
            this.addressStatus = addressStatus;
        }

    }
