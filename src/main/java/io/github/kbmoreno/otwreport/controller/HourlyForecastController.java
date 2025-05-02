package io.github.kbmoreno.otwreport.controller;

import io.github.kbmoreno.otwreport.dto.HourlyForecastDto;
import io.github.kbmoreno.otwreport.service.HourlyForecastService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/forecast/hourly")
public class HourlyForecastController {
    private final HourlyForecastService hourlyForecastService;

    public HourlyForecastController(HourlyForecastService hourlyForecastService) {
        this.hourlyForecastService = hourlyForecastService;
    }

    @GetMapping(params = {"lat", "lon"})
    public HourlyForecastDto getHourlyForecastByGeo(@RequestParam double lat,
                                                    @RequestParam double lon) {
        return hourlyForecastService.getForecastDataByGeo(lat, lon);
    }

    @GetMapping(params = {"city-name"})
    public HourlyForecastDto getHourlyForecastByName(@RequestParam(value = "city-name") String cityName,
                                                     @RequestParam(value = "country-code", required = false) String countryCode) {
        return hourlyForecastService.getForecastDataByName(cityName, countryCode);
    }
}
