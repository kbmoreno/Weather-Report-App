package io.github.kbmoreno.otwreport.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.kbmoreno.otwreport.dto.AirQualityDto;
import io.github.kbmoreno.otwreport.dto.GeocodingDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OwmDtoMapper {
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
