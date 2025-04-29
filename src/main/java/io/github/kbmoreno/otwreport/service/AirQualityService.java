package io.github.kbmoreno.otwreport.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.kbmoreno.otwreport.dto.AirQualityDto;
import io.github.kbmoreno.otwreport.dto.GeocodingDto;
import io.github.kbmoreno.otwreport.mapper.OwmDtoMapper;
import io.github.kbmoreno.otwreport.validator.OwmResponseValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AirQualityService {
    @Value("${owm.key}")
    private String owmKey;

    public final RestTemplate restTemplate;
    public final GeocodingService geocodingService;
    public final OwmDtoMapper mapper;
    public final OwmResponseValidator validator;

    public AirQualityService(RestTemplateBuilder restTemplateBuilder,
                             GeocodingService geocodingService,
                             OwmDtoMapper mapper,
                             OwmResponseValidator validator
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.geocodingService = geocodingService;
        this.mapper = mapper;
        this.validator = validator;
    }

    public AirQualityDto getAirQualityDataByGeo(double lat, double lon) {
        String url = String.format("http://api.openweathermap.org/data/2.5/air_pollution?lat=%s&lon=%s&appid=%s", lat, lon, owmKey);
        ResponseEntity<JsonNode> httpResponse = restTemplate.getForEntity(url, JsonNode.class);

        JsonNode jsonBody = httpResponse.getBody();
        validator.validateAirQualityData(jsonBody);
        return mapper.mapAirQualityData(jsonBody);
    }

    public AirQualityDto getAirQualityDataByName(String cityName, String countryCode) {
        GeocodingDto locationData = geocodingService.getCoordinates(cityName, countryCode);

        double lat = locationData.lat();
        double lon = locationData.lon();
        String url = String.format("http://api.openweathermap.org/data/2.5/air_pollution?lat=%s&lon=%s&appid=%s", lat, lon, owmKey);
        ResponseEntity<JsonNode> httpResponse = restTemplate.getForEntity(url, JsonNode.class);

        JsonNode jsonBody = httpResponse.getBody();
        validator.validateAirQualityData(jsonBody);
        return mapper.mapAirQualityData(jsonBody);
    }
}
