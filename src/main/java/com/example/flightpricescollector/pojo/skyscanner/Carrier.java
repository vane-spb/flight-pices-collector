package com.example.flightpricescollector.pojo.skyscanner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Carrier {
    int carrierId;
    String name;

    @JsonProperty("CarrierId")
    public int getCarrierId() {
        return this.carrierId;
    }

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }

    @JsonProperty("Name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
