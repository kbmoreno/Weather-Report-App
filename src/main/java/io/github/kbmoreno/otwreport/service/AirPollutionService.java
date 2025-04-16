package io.github.kbmoreno.otwreport.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.kbmoreno.otwreport.dto.AirPollutionDTO;
import io.github.kbmoreno.otwreport.exception.InvalidApiKeyException;
import io.github.kbmoreno.otwreport.validator.OwmResponseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AirPollutionService {
    private static final Logger log = LoggerFactory.getLogger(AirPollutionService.class);
    @Value("${owm.key}")
    private String apiKey;

    public final RestTemplate restTemplate;
    public final OwmResponseValidator validator;

    public AirPollutionService(
            RestTemplateBuilder restTemplateBuilder,
            OwmResponseValidator validator
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.validator = validator;
    }

    public AirPollutionDTO getAirPollutionData(double lat, double lon) {
        String url = String.format(
                "http://api.openweathermap.org/data/2.5/air_pollution?lat=%s&lon=%s&appid=%s",
                lat,
                lon,
                apiKey
        );

        try {
            ResponseEntity<JsonNode> httpResponse = restTemplate.getForEntity(url, JsonNode.class);
            JsonNode jsonBody = httpResponse.getBody();
            validator.validateAirPollutionResponse(jsonBody);
            return mapToDto(jsonBody);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                log.error("Invalid API Key used in Open Weather Map Air Pollution API");
                throw new InvalidApiKeyException("An upstream error occurred. Please try again later.");
            }

            throw e;
        }
    }

    public AirPollutionDTO mapToDto(JsonNode jsonBody) {
        return new AirPollutionDTO(
                new AirPollutionDTO.Main(
                        jsonBody.at("/list/0/main/aqi").asInt()
                ),
                new AirPollutionDTO.Components(
                        jsonBody.at("/list/0/components/co").asDouble(),
                        jsonBody.at("/list/0/components/no").asDouble(),
                        jsonBody.at("/list/0/components/no2").asDouble(),
                        jsonBody.at("/list/0/components/o3").asDouble(),
                        jsonBody.at("/list/0/components/so2").asDouble(),
                        jsonBody.at("/list/0/components/pm2_5").asDouble(),
                        jsonBody.at("/list/0/components/pm10").asDouble(),
                        jsonBody.at("/list/0/components/nh3").asDouble()
                )
        );
    }
}
