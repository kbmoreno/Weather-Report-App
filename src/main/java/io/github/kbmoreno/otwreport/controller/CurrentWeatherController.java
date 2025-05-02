package io.github.kbmoreno.otwreport.controller;

import io.github.kbmoreno.otwreport.dto.CurrentWeatherDto;
import io.github.kbmoreno.otwreport.service.CurrentWeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/weather/current")
public class CurrentWeatherController {
    private final CurrentWeatherService currentWeatherService;

    public CurrentWeatherController(CurrentWeatherService currentWeatherService) {
        this.currentWeatherService = currentWeatherService;
    }

    @GetMapping(params = {"lat", "lon"})
    public CurrentWeatherDto getCurrentWeatherByGeo(@RequestParam double lat,
                                                    @RequestParam double lon) {
        return currentWeatherService.getWeatherDataByGeo(lat, lon);
    }

    @GetMapping(params = {"city-name"})
    public CurrentWeatherDto getCurrentWeatherByName(@RequestParam(value = "city-name") String cityName,
                                                     @RequestParam(value = "country-code", required = false) String countryCode) {
        return currentWeatherService.getWeatherDataByName(cityName, countryCode);
    }
}
