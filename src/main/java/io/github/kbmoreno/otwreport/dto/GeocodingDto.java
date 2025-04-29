package io.github.kbmoreno.otwreport.dto;

public record GeocodingDto(
        Double lat,
        Double lon,
        String cityName,
        String countryCode
) {}
