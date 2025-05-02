package io.github.kbmoreno.otwreport.dto;

import java.util.List;

public record HourlyForecastDto(Double lat, Double lon, List<Reports> reports) {
    public record Reports(
            Integer weatherCode,
            String condition,
            String description,
            Double temp,
            Integer dt
    ) {}
}