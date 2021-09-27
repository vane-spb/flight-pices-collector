package com.example.flightpricescollector.pojo.skyscanner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkyscanneerResponse {

    @JsonProperty("Quotes")
    List<Quote> quotes;

    @JsonProperty("Carriers")
    List<Carrier> carriers;

    @JsonProperty("Places")
    List<Place> places;

    @JsonProperty("Currencies")
    List<Currency> currencies;

    @JsonProperty("Routes")
    List<Route> routes;
}
