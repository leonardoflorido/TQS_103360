package tqs.backend.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tqs.backend.model.Geocoding;

@Component
public class GeocodingApi {
    private GeocodingApi() {
    }

    public static Geocoding getGeocoding(String city, String country) {
        String url = String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s,%s&limit=1&appid=e3d8d7c29208f68b6e4fc07bbcf2f703", city, country);

        // Make the API call
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        // Return the geocoding data or null if there was an error
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            return new Geocoding(root.get(0).get("lat").asDouble(), root.get(0).get("lon").asDouble());
        } catch (Exception e) {
            return null;
        }
    }
}
