package com.example.flightpricescollector.pojo.skyscanner;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
public class Route{

    @JsonProperty("Price")
    int price;

    @JsonProperty("QuoteDateTime")
    Date quoteDateTime;

    @JsonProperty("OriginId")
    int originId;

    @JsonProperty("DestinationId")
    int destinationId;

    @JsonProperty("QuoteIds")
    List<Integer> quoteIds;
}
