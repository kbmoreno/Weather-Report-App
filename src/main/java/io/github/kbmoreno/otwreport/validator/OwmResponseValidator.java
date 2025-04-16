package io.github.kbmoreno.otwreport.validator;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.kbmoreno.otwreport.exception.ExternalApiDataNotFoundException;
import io.github.kbmoreno.otwreport.exception.InvalidApiKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OwmResponseValidator {

    private static final Logger log = LoggerFactory.getLogger(OwmResponseValidator.class);

    public void validateAirPollutionResponse(JsonNode jsonBody) {
        if (jsonBody == null) {
            throw new ExternalApiDataNotFoundException("No response body received from the Air Pollution API.");
        }

        if (jsonBody.at("/cod").asInt() == 401) {
            log.error("Invalid API Key used in Open Weather Map Air Pollution API");
            throw new InvalidApiKeyException("An upstream error occurred. Please try again later.");
        }
    }

    public void validateCurrentWeatherResponse(JsonNode jsonBody) {
        if (jsonBody == null) {
            throw new ExternalApiDataNotFoundException("No response body received from the Current Weather API.");
        }

        // will be updated with more logic once Current Weather Service has been created
    }

    public void validateForecastWeatherResponse(JsonNode jsonBody) {
        if (jsonBody == null) {
            throw new ExternalApiDataNotFoundException("No response body received from the Forecast Weather API.");
        }

        // will be updated with more logic once Forecast Weather Service has been created
    }

}
