package com.codebele.aahara.Models.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Codebele on 28-May-20.
 */
public class FoodModel {
    public FoodModel(String restName, String restId, String restAddress, String restRating) {
        this.restName = restName;
        this.restId = restId;
        this.restAddress = restAddress;
        this.restRating = restRating;
    }

    @SerializedName("rest_name")
    @Expose
    private String restName;
    @SerializedName("rest_id")
    @Expose
    private String restId;
    @SerializedName("rest_address")
    @Expose
    private String restAddress;
    @SerializedName("rest_rating")
    @Expose
    private String restRating;

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getRestId() {
        return restId;
    }

    public void setRestId(String restId) {
        this.restId = restId;
    }

    public String getRestAddress() {
        return restAddress;
    }

    public void setRestAddress(String restAddress) {
        this.restAddress = restAddress;
    }

    public String getRestRating() {
        return restRating;
    }

    public void setRestRating(String restRating) {
        this.restRating = restRating;
    }
}
