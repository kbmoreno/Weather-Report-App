package io.github.kbmoreno.otwreport.controller;

import io.github.kbmoreno.otwreport.dto.AirQualityDto;
import io.github.kbmoreno.otwreport.service.AirQualityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/weather/air-quality")
public class AirQualityController {
    private final AirQualityService airQualityService;

    public AirQualityController(AirQualityService airQualityService) {
        this.airQualityService = airQualityService;
    }

    @GetMapping(params = {"lat", "lon"})
    public AirQualityDto getAirPollutionByGeo(@RequestParam double lat,
                                              @RequestParam double lon) {
        return airQualityService.getAirQualityDataByGeo(lat, lon);
    }

    @GetMapping(params = {"city-name"})
    public AirQualityDto getAirPollutionByName(@RequestParam(value = "city-name") String cityName,
                                               @RequestParam(value = "country-code", required = false) String countryCode) {
        return airQualityService.getAirQualityDataByName(cityName, countryCode);
    }
}
