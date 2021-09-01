package com.example.flightpricescollector;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class FlightPricesCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightPricesCollectorApplication.class, args);
    }

}
