package com.codebele.aahara.Models.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ViewAddressModel implements Serializable {
    boolean isSelected;

    public ViewAddressModel(String skAddressId, String addressUserId, String addressName, String city, String pincode, String landmark) {
        this.skAddressId = skAddressId;
        this.addressUserId = addressUserId;
        this.addressName = addressName;
        this.city = city;
        this.pincode = pincode;
        this.landmark = landmark;
    }

    @SerializedName("sk_address_id")
    @Expose
    private String skAddressId;
    @SerializedName("address_userId")
    @Expose
    private String addressUserId;
    @SerializedName("address_name")
    @Expose
    private String addressName;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @SerializedName("landmark")
    @Expose
    private String landmark;

    public String getSkAddressId() {
        return skAddressId;
    }

    public void setSkAddressId(String skAddressId) {
        this.skAddressId = skAddressId;
    }

    public String getAddressUserId() {
        return addressUserId;
    }

    public void setAddressUserId(String addressUserId) {
        this.addressUserId = addressUserId;
    }

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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}