package com.codebele.aahara.ResponsePojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpVerifyPojo {

    @SerializedName("sk_user_id")
    @Expose
    private String skUserId;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("signup_date")
    @Expose
    private String signupDate;
    @SerializedName("user_status")
    @Expose
    private String userStatus;
    @SerializedName("otp")
    @Expose
    private String otp;


    public OtpVerifyPojo(String skUserId, String fullName, String mobile, String email, String signupDate, String userStatus, String otp) {
        super();
        this.skUserId = skUserId;
        this.fullName = fullName;
        this.mobile = mobile;
        this.email = email;
        this.signupDate = signupDate;
        this.userStatus = userStatus;
        this.otp = otp;
    }

    public String getSkUserId() {
        return skUserId;
    }

    public void setSkUserId(String skUserId) {
        this.skUserId = skUserId;
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

    public String getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(String signupDate) {
        this.signupDate = signupDate;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}

