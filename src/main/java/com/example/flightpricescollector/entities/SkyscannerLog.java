package com.example.flightpricescollector.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Builder
@Table("skyscanner_log")
@ToString
public class SkyscannerLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column("flight_price")
    Double flightPrice;
    @Column("is_direct")
    Boolean isDirect;
    @Column("departure_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date departureDate;

    @Column("carrier_id")
    Integer carrierId;
    @Column("carrier_name")
    String carrierName;

    @Column("departure_place_id")
    Integer departurePlaceId;
    @Column("departure_place_iata_code")
    String departurePlaceIataCode;
    @Column("departure_place_name")
    String departurePlaceName;
    @Column("departure_city_name")
    String departureCityName;

    @Column("destination_place_id")
    Integer destinationPlaceId;
    @Column("destination_place_iata_code")
    String destinationPlaceIataCode;
    @Column("destination_place_name")
    String destinationPlaceName;
    @Column("destination_city_name")
    String destinationCityName;

    @Column("quote_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    Date quoteDateTime;

    @Column("request_time")
    @Temporal(TemporalType.TIMESTAMP)
    Date currentTime = new Date();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkyscannerLog that = (SkyscannerLog) o;
        return Objects.equals(flightPrice, that.flightPrice) &&
                Objects.equals(isDirect, that.isDirect) &&
                Objects.equals(departureDate, that.departureDate) &&
                Objects.equals(carrierId, that.carrierId) &&
                Objects.equals(carrierName, that.carrierName) &&
                Objects.equals(departurePlaceId, that.departurePlaceId) &&
                Objects.equals(departurePlaceIataCode, that.departurePlaceIataCode) &&
                Objects.equals(departurePlaceName, that.departurePlaceName) &&
                Objects.equals(departureCityName, that.departureCityName) &&
                Objects.equals(destinationPlaceId, that.destinationPlaceId) &&
                Objects.equals(destinationPlaceIataCode, that.destinationPlaceIataCode) &&
                Objects.equals(destinationPlaceName, that.destinationPlaceName) &&
                Objects.equals(destinationCityName, that.destinationCityName) &&
                Objects.equals(quoteDateTime, that.quoteDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightPrice,
                isDirect,
                departureDate,
                carrierId,
                carrierName,
                departurePlaceId,
                departurePlaceIataCode,
                departurePlaceName,
                departureCityName,
                destinationPlaceId,
                destinationPlaceIataCode,
                destinationPlaceName,
                destinationCityName,
                quoteDateTime);
    }
}
