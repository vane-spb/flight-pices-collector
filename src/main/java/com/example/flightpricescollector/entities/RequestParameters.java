package com.example.flightpricescollector.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Currency;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Builder
@ToString
@Table("destinations_for_skyscanner")
public class RequestParameters {

    @Id
    private Integer id;

    @Column("origin_place")
    private String originPlace;

    @Column("destination_place")
    private String destinationPlace;
    private Date date;
    private String country;
    private String currency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestParameters that = (RequestParameters) o;
        return Objects.equals(originPlace, that.originPlace) &&
                Objects.equals(destinationPlace, that.destinationPlace) &&
                Objects.equals(date, that.date) &&
                Objects.equals(country, that.country) &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originPlace, destinationPlace, date, country, currency);
    }
}
