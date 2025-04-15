package io.github.kbmoreno.otwreport.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.kbmoreno.otwreport.dto.AirPollutionDTO;
import io.github.kbmoreno.otwreport.exception.IncompleteApiResponseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AirPollutionService {
    @Value("${weather.key}")
    private String apiKey;

    public final RestTemplate restTemplate;

    public AirPollutionService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public AirPollutionDTO getAirPollutionData(double lat, double lon) {
        String url = String.format(
                "http://api.openweathermap.org/data/2.5/air_pollution?lat=%s&lon=%s&appid=%s",
                lat,
                lon,
                apiKey
        );

        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode json = response.getBody();

        if (json == null || !json.has("list")) {
            throw new IncompleteApiResponseException("No data point for Air Pollution found.");
        }

        AirPollutionDTO airPollutionDTO = new AirPollutionDTO(
                new AirPollutionDTO.Main(json.at("/list/0/main/aqi").asInt()),
                new AirPollutionDTO.Components(
                        json.at("/list/0/components/co").asDouble(),
                        json.at("/list/0/components/no").asDouble(),
                        json.at("/list/0/components/no2").asDouble(),
                        json.at("/list/0/components/o3").asDouble(),
                        json.at("/list/0/components/so2").asDouble(),
                        json.at("/list/0/components/pm2_5").asDouble(),
                        json.at("/list/0/components/pm10").asDouble(),
                        json.at("/list/0/components/nh3").asDouble()
                )
        );
//        dto.setAqi(json.at("/list/0/main/aqi").asInt());
//        dto.setCo(json.at("/list/0/components/co").asDouble());
//        dto.setNo(json.at("/list/0/components/no").asDouble());
//        dto.setNo2(json.at("/list/0/components/no2").asDouble());
//        dto.setO3(json.at("/list/0/components/o3").asDouble());
//        dto.setSo2(json.at("/list/0/components/so2").asDouble());
//        dto.setPm2_5(json.at("/list/0/components/pm2_5").asDouble());
//        dto.setPm10(json.at("/list/0/components/pm10").asDouble());
//        dto.setNh3(json.at("/list/0/components/nh3").asDouble());

        return airPollutionDTO;
    }
}
