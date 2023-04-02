package tqs.backend.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import tqs.backend.model.AirPollution;

import java.util.ArrayList;

public class AirPollutionApi {
    private static final String API_KEY = "e3d8d7c29208f68b6e4fc07bbcf2f703";

    public static AirPollution getAirPollution(double lat, double lon) {
        String url = String.format("https://api.openweathermap.org/data/2.5/air_pollution?lat=%f&lon=%f&appid=%s", lat, lon, API_KEY);

        // Make the API call
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        // Return the air pollution data or null if there was an error
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            return new AirPollution(root.get("list").get(0).get("main").get("aqi").asInt(), root.get("list").get(0).get("components").get("co").asDouble(), root.get("list").get(0).get("components").get("no").asDouble(), root.get("list").get(0).get("components").get("no2").asDouble(), root.get("list").get(0).get("components").get("o3").asDouble(), root.get("list").get(0).get("components").get("so2").asDouble(), root.get("list").get(0).get("components").get("pm2_5").asDouble(), root.get("list").get(0).get("components").get("pm10").asDouble(), root.get("list").get(0).get("components").get("nh3").asDouble());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<AirPollution> getAirPollutionHistory(double lat, double lon, int start, int end) {
        String url = String.format("https://api.openweathermap.org/data/2.5/air_pollution/history?lat=%f&lon=%f&start=%d&end=%d&appid=%s", lat, lon, start, end, API_KEY);

        // Make the API call
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        // Return the air pollution history data or null if there was an error
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            ArrayList<AirPollution> airPollutionHistory = new ArrayList<>();
            root.get("list").forEach(node -> airPollutionHistory.add(new AirPollution(
                    node.get("main").get("aqi").asInt(),
                    node.get("components").get("co").asDouble(),
                    node.get("components").get("no").asDouble(),
                    node.get("components").get("no2").asDouble(),
                    node.get("components").get("o3").asDouble(),
                    node.get("components").get("so2").asDouble(),
                    node.get("components").get("pm2_5").asDouble(),
                    node.get("components").get("pm10").asDouble(),
                    node.get("components").get("nh3").asDouble()
            )));
            return airPollutionHistory;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
