package com.codebele.aahara.Models.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Codebele on 04-Jul-20.
 */
public class CityNamePojo {


        @SerializedName("city_id")
        @Expose
        private String cityId;
        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("state_name")
        @Expose
        private String stateName;
        @SerializedName("state_id")
        @Expose
        private String stateId;
        @SerializedName("country_id")
        @Expose
        private String countryId;
        @SerializedName("pin_code")
        @Expose
        private String pinCode;
        @SerializedName("city_status")
        @Expose
        private String cityStatus;

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
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

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getPinCode() {
            return pinCode;
        }

        public void setPinCode(String pinCode) {
            this.pinCode = pinCode;
        }

        public String getCityStatus() {
            return cityStatus;
        }

        public void setCityStatus(String cityStatus) {
            this.cityStatus = cityStatus;
        }
}
