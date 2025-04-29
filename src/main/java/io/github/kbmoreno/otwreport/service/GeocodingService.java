package io.github.kbmoreno.otwreport.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.kbmoreno.otwreport.dto.GeocodingDto;
import io.github.kbmoreno.otwreport.mapper.OwmDtoMapper;
import io.github.kbmoreno.otwreport.validator.OwmResponseValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingService {
    @Value("${owm.key}")
    private String owmKey;

    public final RestTemplate restTemplate;
    public final OwmDtoMapper mapper;
    public final OwmResponseValidator validator;

    GeocodingService(RestTemplateBuilder restTemplateBuilder,
                     OwmDtoMapper mapper,
                     OwmResponseValidator validator
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.mapper = mapper;
        this.validator = validator;
    }

    public GeocodingDto getCoordinates(String cityName, String countryCode) {
        String url;
        if (countryCode != null) {
            url = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s,%s&limit=1&appid=%s", cityName, countryCode, owmKey);
        } else {
            url = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s", cityName, owmKey);
        }
        ResponseEntity<JsonNode> httpResponse = restTemplate.getForEntity(url, JsonNode.class);

        JsonNode jsonBody = httpResponse.getBody();
        validator.validateGeocodingData(jsonBody);
        return mapper.mapGeocodingData(jsonBody);
    }

    public GeocodingDto getToponym(double lat, double lon) {
        String url = String.format("http://api.openweathermap.org/geo/1.0/reverse?lat=%s&lon=%s&limit=1&appid=%s", lat, lon, owmKey);
        ResponseEntity<JsonNode> httpResponse = restTemplate.getForEntity(url, JsonNode.class);

        JsonNode jsonBody = httpResponse.getBody();
        validator.validateGeocodingData(jsonBody);
        return mapper.mapGeocodingData(jsonBody);
    }
}
