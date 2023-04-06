package tqs.backend.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tqs.backend.model.AirPollution;

@Component
public class AirPollutionApi {
    private AirPollutionApi() {
    }

    public static AirPollution getAirPollution(double lat, double lon) {
        String url = String.format("https://api.openweathermap.org/data/2.5/air_pollution?lat=%f&lon=%f&appid=e3d8d7c29208f68b6e4fc07bbcf2f703", lat, lon);

        // Make the API call
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        // Return the air pollution data or null if there was an error
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode components = root.get("list").get(0).get("components");
            return new AirPollution(
                    root.get("list").get(0).get("main").get("aqi").asInt(),
                    components.get("co").asDouble(),
                    components.get("no").asDouble(),
                    components.get("no2").asDouble(),
                    components.get("o3").asDouble(),
                    components.get("so2").asDouble(),
                    components.get("pm2_5").asDouble(),
                    components.get("pm10").asDouble(),
                    components.get("nh3").asDouble()
            );
        } catch (Exception e) {
            return null;
        }
    }
}
