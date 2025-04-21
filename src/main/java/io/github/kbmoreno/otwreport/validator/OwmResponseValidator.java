package io.github.kbmoreno.otwreport.validator;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.kbmoreno.otwreport.exception.MissingJsonNodeException;
import io.github.kbmoreno.otwreport.exception.NullResponseException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OwmResponseValidator {
    private static final Logger log = LoggerFactory.getLogger(OwmResponseValidator.class);

    @Autowired
    private HttpServletRequest request;

    public void validateAirPollutionData(JsonNode jsonBody) {
        if (jsonBody == null) {
            logNullResponse();
        }

        JsonNode listNode = jsonBody.path("list");
        if (listNode.isMissingNode() || !listNode.isArray()) {
            logMissingNode(jsonBody, "list");
        }

        JsonNode mainNode = listNode.get(0).path("main");
        if (mainNode.isMissingNode() || mainNode.isNull()) {
            logMissingNode(jsonBody, "main");
        }

        JsonNode componentNode = listNode.get(0).path("components");
        if (componentNode.isMissingNode() || componentNode.isNull()) {
            logMissingNode(jsonBody, "components");
        }
    }

    public void logNullResponse() {
        log.error("No data found in the HTTP response of Open Weather Map's Air Pollution API");
        log.debug("Client Request: {}", request.getRequestURL());
        throw new NullResponseException("Currently unable to retrieve air pollution data. Please try again later.");
    }

    public void logMissingNode(JsonNode jsonBody, String nodeName) {
        log.error("Missing Node: '{}', in HTTP response body", nodeName);
        log.debug("Response: {}", jsonBody);
        throw new MissingJsonNodeException("Currently unable to retrieve air pollution data. Please try again later.");
    }
}
