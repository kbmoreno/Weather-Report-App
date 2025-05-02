package io.github.kbmoreno.otwreport.dto;

public record CurrentWeatherDto(Double lat, Double lon, Report report) {
    public record Report(
            Integer weatherCode,
            String condition,
            String description,
            Double temperature,
            Double heatIndex,
            Double humidity,
            Integer visibility,
            Double windSpeed,
            Double windDegree,
            Double windGust,
            Integer cloudiness,
            Integer dt
    ) {}
}



