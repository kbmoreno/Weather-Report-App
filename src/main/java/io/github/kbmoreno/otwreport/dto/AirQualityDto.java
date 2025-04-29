package io.github.kbmoreno.otwreport.dto;

public record AirQualityDto(Double lat, Double lon, Report report) {
    public record Report(Integer aqi, Pollutants pollutants) {
        public record Pollutants(
                Double co,
                Double no,
                Double no2,
                Double o3,
                Double so2,
                Double pm2_5,
                Double pm10,
                Double nh3
        ) {}
    }
}