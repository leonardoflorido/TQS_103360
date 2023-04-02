package tqs.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tqs.backend.api.AirPollutionApi;
import tqs.backend.api.GeocodingApi;
import tqs.backend.model.AirPollution;
import tqs.backend.model.Geocoding;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class AirPollutionController {
    @GetMapping("/air_pollution")
    public AirPollution getAirPollution(@RequestParam String city, @RequestParam String country) {
        // Get the geolocation of the city
        Geocoding geocoding = GeocodingApi.getGeocoding(city, country);

        // Check if the geocoding data is null and throw a runtime exception if it is
        if (geocoding == null) {
            throw new RuntimeException("City not found");
        }

        // Return the air pollution data
        return AirPollutionApi.getAirPollution(geocoding.getLat(), geocoding.getLon());
    }

    @GetMapping("/air_pollution/history")
    public ArrayList<AirPollution> getAirPollutionHistory(@RequestParam String city, @RequestParam String country, @RequestParam int start, @RequestParam int end) {
        // Get the geolocation of the city
        Geocoding geocoding = GeocodingApi.getGeocoding(city, country);

        // Check if the geocoding data is null and throw a runtime exception if it is
        if (geocoding == null) {
            throw new RuntimeException("City not found");
        }

        // Return the air pollution data
        return AirPollutionApi.getAirPollutionHistory(geocoding.getLat(), geocoding.getLon(), start, end);
    }
}
