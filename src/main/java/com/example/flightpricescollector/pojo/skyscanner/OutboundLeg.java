package com.example.flightpricescollector.pojo.skyscanner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutboundLeg {

    @JsonProperty("CarrierIds")
    List<Integer> carrierIds;

    @JsonProperty("OriginId")
    int originId;

    @JsonProperty("DestinationId")
    int destinationId;

    @JsonProperty("DepartureDate")
    Date departureDate;
}
