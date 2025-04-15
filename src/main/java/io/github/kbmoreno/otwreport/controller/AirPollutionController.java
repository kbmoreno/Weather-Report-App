package io.github.kbmoreno.otwreport.controller;

import io.github.kbmoreno.otwreport.dto.AirPollutionDTO;
import io.github.kbmoreno.otwreport.service.AirPollutionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data/ap")
public class AirPollutionController {
    private final AirPollutionService airPollutionService;

    public AirPollutionController(AirPollutionService airPollutionService) {
        this.airPollutionService = airPollutionService;
    }

    @GetMapping
    public AirPollutionDTO getAirPollution(@RequestParam double lat,
                                           @RequestParam double lon) {
        return airPollutionService.getAirPollutionData(lat, lon);
    }
}
