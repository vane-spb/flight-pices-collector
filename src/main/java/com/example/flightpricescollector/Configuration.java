package com.example.flightpricescollector;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Configuration {
    public static final long DATA_GRAB_DELAY_MILLIS = 900000;
    public static final String SKYSCANNER_SEARCH_HOST = "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com";
    public static final String SKYSCANNER_API_KEY = System.getProperty("SECRET");
    public static final int SKYSCANNER_REQUESTS_LIMIT = 40;
}
