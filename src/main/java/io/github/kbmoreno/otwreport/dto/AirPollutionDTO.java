package io.github.kbmoreno.otwreport.dto;

public record AirPollutionDTO(Main main, Components components) {
    public record Main(int aqi) {}

    public record Components(
            double co,
            double no,
            double no2,
            double o3,
            double so2,
            double pm2_5,
            double pm10,
            double nh3
    ) {}
}