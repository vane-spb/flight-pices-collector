package com.example.flightpricescollector.services;

import com.example.flightpricescollector.entities.RequestParameters;
import com.example.flightpricescollector.entities.SkyscannerLog;
import com.example.flightpricescollector.pojo.skyscanner.SkyscanneerResponse;
import com.example.flightpricescollector.repository.RequestParametersRepository;
import com.example.flightpricescollector.repository.SkyscannerLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
class SkyscannerDataCollectorServiceTest {

    private static final ObjectMapper om = new ObjectMapper();

    private static final RequestParameters SPB_TO_MSK_ROUTE_PARAMETERS = RequestParameters.builder()
            .id(0)
            .destinationPlace("VKO")
            .originPlace("LED")
            .date(new Date())
            .country("RU")
            .currency("RUB")
            .build();
    private static final String[] DATE_FORMAT = {"yyyy-MM-dd HH:mm:ss"};

    private SkyscannerDataCollectorService collectorService;

    @BeforeEach
    void initialiseCollector() {
        RequestParametersRepository requestParametersRepository = mock(RequestParametersRepository.class);
        SkyscannerLogRepository skyscannerLogRepository = mock(SkyscannerLogRepository.class);

        collectorService = new SkyscannerDataCollectorService(
                skyscannerLogRepository, requestParametersRepository);
    }

    @Test
    void extractDataFromSkyscannerResponse() throws IOException, ParseException {
        final List<SkyscannerLog> expected = List.of(
                SkyscannerLog.builder()
                        .flightPrice(3300.)
                        .isDirect(true)
                        .departureDate(DateUtils.parseDate("2021-09-01 03:00:00", DATE_FORMAT))
                        .carrierId(1717)
                        .carrierName("Aeroflot")
                        .departurePlaceId(65540)
                        .departurePlaceIataCode("LED")
                        .departurePlaceName("St Petersburg Pulkovo")
                        .departureCityName("St Petersburg")
                        .destinationPlaceId(88879)
                        .destinationPlaceIataCode("VKO")
                        .destinationPlaceName("Moscow Vnukovo")
                        .destinationCityName("Moscow")
                        .quoteDateTime(DateUtils.parseDate("2021-08-31 22:45:00", DATE_FORMAT))
                        .build()
        );

        final SkyscanneerResponse skyscanneerResponse =
                om.readValue(new File("src/test/resources/Skyscanner/led-to-vko-20210901.json"),
                        SkyscanneerResponse.class);

        final List<SkyscannerLog> actual = collectorService.extractDataFromSkyscannerResponse(skyscanneerResponse);

        assertEquals(expected.get(0), actual.get(0));
    }

    @Test
    void getPricesFromSkyscanner() throws IOException {
        final Response pricesFromSkyscanner = collectorService.getPricesFromSkyscanner(SPB_TO_MSK_ROUTE_PARAMETERS);
        final String responceBody = pricesFromSkyscanner.body().string();

        assertTrue(pricesFromSkyscanner.isSuccessful() && responceBody.contains("RUB"));
        log.info(responceBody);
    }
}