package com.example.flightpricescollector.pojo.skyscanner;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Place {

    @JsonProperty("Name")
    String name;

    @JsonProperty("Type")
    String type;

    @JsonProperty("PlaceId")
    int placeId;

    @JsonProperty("IataCode")
    String iataCode;

    @JsonProperty("SkyscannerCode")
    String skyscannerCode;

    @JsonProperty("CityName")
    String cityName;

    @JsonProperty("CityId")
    String cityId;

    @JsonProperty("CountryName")
    String countryName;
}