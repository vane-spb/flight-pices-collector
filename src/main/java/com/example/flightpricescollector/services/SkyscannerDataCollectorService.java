package com.example.flightpricescollector.services;

import com.example.flightpricescollector.Configuration;
import com.example.flightpricescollector.entities.RequestParameters;
import com.example.flightpricescollector.entities.SkyscannerLog;
import com.example.flightpricescollector.pojo.skyscanner.*;
import com.example.flightpricescollector.repository.RequestParametersRepository;
import com.example.flightpricescollector.repository.SkyscannerLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkyscannerDataCollectorService {
    private static final ObjectMapper om = new ObjectMapper();
    private final SkyscannerLogRepository skyscannerLogRepository;
    private final RequestParametersRepository requestParametersRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final AtomicBoolean isAlreadyRunning = new AtomicBoolean(false);

    @Scheduled(fixedDelay = Configuration.DATA_GRAB_DELAY_MILLIS)
    public void grabDataFromSkyscanner() {
        if (isAlreadyRunning.get()) {
            log.error("Task did not complete till next start!");
            return;
        }
        isAlreadyRunning.set(true);

        try {requestParametersRepository.findAll().forEach(requestParameters -> {
                    Response response = getPricesFromSkyscanner(requestParameters);
                    if (response != null) {
                        try {
                            final SkyscanneerResponse responseObject = om.readValue(response.body().string(), SkyscanneerResponse.class);
                            skyscannerLogRepository.saveAll(extractDataFromSkyscannerResponse(responseObject));
                        } catch (Exception e) {
                            log.error("Cant parse Skyscanner response: ", e);
                        }

                    }
                }
        );} finally {
            isAlreadyRunning.set(false);
        }

    }

    protected List<SkyscannerLog> extractDataFromSkyscannerResponse(SkyscanneerResponse skyscanneerResponse) {
        return skyscanneerResponse.
                getQuotes().stream()
                .filter(Quote::isDirect)
                .map(quote -> {
                    final OutboundLeg outboundLeg = quote.getOutboundLeg();

                    final Place originPlace = skyscanneerResponse.getPlaces().stream()
                            .filter(place -> place.getPlaceId() == outboundLeg.getOriginId())
                            .findFirst().get();
                    final Place destinationPlace = skyscanneerResponse.getPlaces().stream()
                            .filter(place -> place.getPlaceId() == outboundLeg.getDestinationId())
                            .findFirst().get();

                    final List<Carrier> carriers = skyscanneerResponse.getCarriers().stream()
                            .filter(carrier -> outboundLeg.getCarrierIds().contains(carrier.getCarrierId()))
                            .toList();

                    return SkyscannerLog.builder()
                            .flightPrice(quote.getMinPrice())
                            .isDirect(quote.isDirect())
                            .carrierId(carriers.size() == 1 ? carriers.get(0).getCarrierId() : null)
                            .carrierName(carriers.stream().map(Carrier::getName).collect(Collectors.joining(";")))
                            .departureDate(outboundLeg.getDepartureDate())
                            .departureCityName(originPlace.getCityName())
                            .departurePlaceIataCode(originPlace.getIataCode())
                            .departurePlaceId(outboundLeg.getOriginId())
                            .departurePlaceName(originPlace.getName())
                            .destinationCityName(destinationPlace.getCityName())
                            .destinationPlaceIataCode(destinationPlace.getIataCode())
                            .destinationPlaceId(outboundLeg.getDestinationId())
                            .destinationPlaceName(destinationPlace.getName())
                            .quoteDateTime(quote.getQuoteDateTime())
                            .currentTime(new Date())
                            .build();
                })
                .peek(skyscannerLog -> log.info("Extracted: " + skyscannerLog))
                .toList();
    }

    protected Response getPricesFromSkyscanner(RequestParameters requestParameters) {
        try {
            Thread.sleep(60*1000/Configuration.SKYSCANNER_REQUESTS_LIMIT);
        } catch (InterruptedException e) {
        }

        log.info("Requesting: " + requestParameters);

        //example url
        //https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browseroutes/v1.0/
        // US/USD/en-US/SFO-sky/ORD-sky/2021-09-01
        final String url = String.format("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browseroutes/v1.0/" +
                        "%s/%s/en-US/%s-sky/%s-sky/%s",
                requestParameters.getCountry(),
                requestParameters.getCurrency(),
                requestParameters.getOriginPlace(),
                requestParameters.getDestinationPlace(),
                dateFormat.format(requestParameters.getDate()));

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-rapidapi-host", Configuration.SKYSCANNER_SEARCH_HOST)
                .addHeader("x-rapidapi-key", Configuration.SKYSCANNER_API_KEY)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            log.error("Can't grab data from Skyscanner: ", e);
        }
        return response;
    }
}
