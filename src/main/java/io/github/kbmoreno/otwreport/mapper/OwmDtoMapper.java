package io.github.kbmoreno.otwreport.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.kbmoreno.otwreport.dto.CurrentWeatherDto;
import io.github.kbmoreno.otwreport.dto.HourlyForecastDto;
import io.github.kbmoreno.otwreport.dto.AirQualityDto;
import io.github.kbmoreno.otwreport.dto.GeocodingDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OwmDtoMapper {

    public CurrentWeatherDto mapCurrentWeatherData(JsonNode jsonBody) {
        return new CurrentWeatherDto(
                safeGetDouble(jsonBody, "/coord/lat"),
                safeGetDouble(jsonBody, "/coord/lon"),
                new CurrentWeatherDto.Report(
                        safeGetInt(jsonBody, "/weather/0/id"),
                        safeGetText(jsonBody, "/weather/0/main"),
                        safeGetText(jsonBody, "/weather/0/description"),
                        safeGetDouble(jsonBody, "/main/temp"),
                        safeGetDouble(jsonBody, "/main/feels_like"),
                        safeGetDouble(jsonBody, "/main/humidity"),
                        safeGetInt(jsonBody, "/visibility"),
                        safeGetDouble(jsonBody, "/wind/speed"),
                        safeGetDouble(jsonBody, "/wind/deg"),
                        safeGetDouble(jsonBody, "/wind/gust"),
                        safeGetInt(jsonBody, "/clouds/all"),
                        safeGetInt(jsonBody, "/dt")
                )
        );
    }

    public HourlyForecastDto mapHourlyForecastData(JsonNode jsonBody) {
        List<HourlyForecastDto.Reports> reports = new ArrayList<>();

        JsonNode listNode = jsonBody.get("list");
        for (JsonNode node : listNode) {
            Integer weatherCode = safeGetInt(node, "/weather/0/id");
            String condition = safeGetText(node, "/weather/0/main");
            String description = safeGetText(node, "/weather/0/description");
            Double temp = safeGetDouble(node, "/main/temp");
            Integer dt = safeGetInt(node, "/dt");

            HourlyForecastDto.Reports data = new HourlyForecastDto.Reports(weatherCode, condition, description, temp, dt);
            reports.add(data);
        }

        return new HourlyForecastDto(
                safeGetDouble(jsonBody, "/city/coord/lat"),
                safeGetDouble(jsonBody, "/city/coord/lon"),
                reports
        );
    }

    public AirQualityDto mapAirQualityData(JsonNode jsonBody) {
        return new AirQualityDto(
                safeGetDouble(jsonBody, "/coord/lat"),
                safeGetDouble(jsonBody, "/coord/lon"),
                new AirQualityDto.Report(
                    safeGetInt(jsonBody, "/list/0/main/aqi"),
                    new AirQualityDto.Report.Pollutants(
                            safeGetDouble(jsonBody, "/list/0/components/co"),
                            safeGetDouble(jsonBody, "/list/0/components/no"),
                            safeGetDouble(jsonBody, "/list/0/components/no2"),
                            safeGetDouble(jsonBody, "/list/0/components/o3"),
                            safeGetDouble(jsonBody, "/list/0/components/so2"),
                            safeGetDouble(jsonBody, "/list/0/components/pm2_5"),
                            safeGetDouble(jsonBody, "/list/0/components/pm10"),
                            safeGetDouble(jsonBody, "/list/0/components/nh3")
                    )
                )
        );
    }

    public GeocodingDto mapGeocodingData(JsonNode jsonBody) {
        return new GeocodingDto(
                safeGetDouble(jsonBody, "/0/lat"),
                safeGetDouble(jsonBody, "/0/lon"),
                safeGetText(jsonBody, "/0/name"),
                safeGetText(jsonBody, "/0/country")
        );
    }

    private String safeGetText(JsonNode node, String path) {
        JsonNode target = node.at(path);
        return (target.isMissingNode() || target.isNull()) ? null : target.asText();
    }

    private Integer safeGetInt(JsonNode node, String path) {
        JsonNode target = node.at(path);
        return (target.isMissingNode() || target.isNull()) ? null : target.asInt();
    }

    private Double safeGetDouble(JsonNode node, String path) {
        JsonNode target = node.at(path);
        return (target.isMissingNode() || target.isNull()) ? null : target.asDouble();
    }
}
