package com.codebele.aahara.Models.Models;

import java.io.Serializable;

public class DeliveryAddressModel implements Serializable {
    String address;

    public DeliveryAddressModel(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DeliveryAddressModel() {

    }


}
