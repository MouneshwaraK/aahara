package com.codebele.aahara.NetworkUtils;

public class ServerResponse<T> {
    private boolean status;
    private String message;
    private String status_code;
    private String otp;
    private T data;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return message;
    }

    public void setStatusMessage(String statusMessage) {
        this.message = statusMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }
}
