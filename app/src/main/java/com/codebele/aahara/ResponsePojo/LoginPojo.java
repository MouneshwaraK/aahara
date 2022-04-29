package com.codebele.aahara.ResponsePojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginPojo implements Serializable {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("status_code")
    @Expose
    private String status_code;
    @SerializedName("data")
    @Expose
    private List list;
//
//    public LoginPojo(Boolean status, String message, String otp,List list) {
//        super();
//        this.status = status;
//        this.message = message;
//        this.otp = otp;
//        this.status_code = status_code;
//        this.list = list;
//
//    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public class List implements Serializable {

        @SerializedName("full_name")
        @Expose
        private String fullName;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("sk_user_id")
        @Expose
        private String sk_user_id;
        @SerializedName("AccessToken")
        @Expose
        private String accessToken;
        @SerializedName("city_name")
        @Expose
        private String city_name;
        @SerializedName("city_id")
        @Expose
        private String cityId;



        public List(String fullName, String mobile, String email,String sk_user_id, String accessToken,String city_name,String cityId) {
            super();
            this.fullName = fullName;
            this.mobile = mobile;
            this.email = email;
            this.sk_user_id=sk_user_id;
            this.accessToken = accessToken;
            this.city_name = city_name;
            this.cityId = cityId;
        }

        public String getSk_user_id() {
            return sk_user_id;
        }

        public void setSk_user_id(String sk_user_id) {
            this.sk_user_id = sk_user_id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }
    }
}

