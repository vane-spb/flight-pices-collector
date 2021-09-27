package com.example.flightpricescollector.pojo.skyscanner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    @JsonProperty("QuoteId")
    int quoteId;

    @JsonProperty("MinPrice")
    double minPrice;

    @JsonProperty("Direct")
    boolean direct;

    @JsonProperty("OutboundLeg")
    OutboundLeg outboundLeg;

    @JsonProperty("QuoteDateTime")
    Date quoteDateTime;
}
