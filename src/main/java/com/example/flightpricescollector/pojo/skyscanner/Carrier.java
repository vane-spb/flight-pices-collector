package com.example.flightpricescollector.pojo.skyscanner;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Carrier{
    @JsonProperty("CarrierId") 
    public int getCarrierId() { 
		 return this.carrierId; } 
    public void setCarrierId(int carrierId) { 
		 this.carrierId = carrierId; } 
    int carrierId;
    @JsonProperty("Name") 
    public String getName() { 
		 return this.name; } 
    public void setName(String name) { 
		 this.name = name; } 
    String name;
}
