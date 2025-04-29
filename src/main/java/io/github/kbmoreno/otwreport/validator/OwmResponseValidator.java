package io.github.kbmoreno.otwreport.validator;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.kbmoreno.otwreport.exception.MissingJsonNodeException;
import io.github.kbmoreno.otwreport.exception.NullResponseException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OwmResponseValidator {
    private static final Logger log = LoggerFactory.getLogger(OwmResponseValidator.class);

    @Autowired
    private HttpServletRequest request;

    public void validateCurrentWeatherData(JsonNode jsonBody) {
        String source = "Current Weather";

        if (jsonBody == null) logNullResponse(source);

        JsonNode weatherNode = jsonBody.path("weather");
        if (weatherNode.isMissingNode() || !weatherNode.isArray()) {
            logMissingNode(jsonBody, "weather", source);
        }

        JsonNode mainNode = jsonBody.path("main");
        if (mainNode.isMissingNode() || mainNode.isNull()) {
            logMissingNode(jsonBody, "main", source);
        }
    }

    public void validateHourlyForecastData(JsonNode jsonBody) {
        String source = "5 day, 3-hour Step Forecast";

        if (jsonBody == null) logNullResponse(source);

        JsonNode listNode = jsonBody.path("list");
        if (listNode.isMissingNode() || !listNode.isArray()) {
            logMissingNode(jsonBody, "list", source);
        }

        for (JsonNode node : listNode) {
            JsonNode weatherNode = node.path("weather");
            if (weatherNode.isMissingNode() || !weatherNode.isArray()) {
                logMissingNode(jsonBody, "weather", source);
            }

            JsonNode mainNode = node.path("main");
            if (mainNode.isMissingNode() || mainNode.isNull()) {
                logMissingNode(jsonBody, "main", source);
            }
        }
    }

    public void validateAirQualityData(JsonNode jsonBody) {
        String source = "Air Quality";

        if (jsonBody == null) logNullResponse(source);

        JsonNode listNode = jsonBody.path("list");
        if (listNode.isMissingNode() || !listNode.isArray()) {
            logMissingNode(jsonBody, "list", source);
        }

        JsonNode mainNode = listNode.get(0).path("main");
        if (mainNode.isMissingNode() || mainNode.isNull()) {
            logMissingNode(jsonBody, "main", source);
        }

        JsonNode componentNode = listNode.get(0).path("components");
        if (componentNode.isMissingNode() || componentNode.isNull()) {
            logMissingNode(jsonBody, "components", source);
        }
    }

    public void validateGeocodingData(JsonNode jsonBody) {
        String source = "Geocoding";

        if (jsonBody == null) logNullResponse(source);

        if (jsonBody.get(0).path("lat").isMissingNode() || jsonBody.get(0).path("lat").isNull()) {
            logMissingNode(jsonBody, "lat", source);
        }

        if (jsonBody.get(0).path("lon").isMissingNode() || jsonBody.get(0).path("lon").isNull()) {
            logMissingNode(jsonBody, "lon", source);
        }

        if (jsonBody.get(0).path("name").isMissingNode() || jsonBody.get(0).path("name").isNull()) {
            logMissingNode(jsonBody, "name", source);
        }

        if (jsonBody.get(0).path("country").isMissingNode() || jsonBody.get(0).path("country").isNull()) {
            logMissingNode(jsonBody, "country", source);
        }
    }

    public void logNullResponse(String source) {
        log.error("No data found in the HTTP response of Open Weather Map's {} API", source);
        log.debug("Client Request: {}", request.getRequestURL());
        throw new NullResponseException("Currently unable to retrieve data. Please try again later.");
    }

    public void logMissingNode(JsonNode jsonBody, String nodeName, String source) {
        log.error("Missing Node from the response of {} API", source);
        log.error("Missing Node: {}", nodeName);
        log.debug("Response: {}", jsonBody.toString());
        throw new MissingJsonNodeException("Currently unable to retrieve data. Please try again later.");
    }
}
