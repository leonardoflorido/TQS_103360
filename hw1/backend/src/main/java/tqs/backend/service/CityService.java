package tqs.backend.service;

import org.springframework.stereotype.Service;
import tqs.backend.api.AirPollutionApi;
import tqs.backend.api.GeocodingApi;
import tqs.backend.cache.CityCache;
import tqs.backend.model.AirPollution;
import tqs.backend.model.City;
import tqs.backend.model.Geocoding;

import java.util.HashMap;

@Service
public class CityService {
    private final CityCache cityCache = new CityCache();

    public City getCity(String cityName, String country) {
        City city = cityCache.get(cityName);

        if (city == null) {
            // Get the geolocation of the city
            Geocoding geocoding = GeocodingApi.getGeocoding(cityName, country);

            // Check if the geocoding data is null and throw a runtime exception if it is
            if (geocoding == null) {
                throw new RuntimeException("City not found");
            }

            // Get air pollution data
            AirPollution airPollution = AirPollutionApi.getAirPollution(geocoding.getLat(), geocoding.getLon());

            // Create a new city object
            assert airPollution != null;
            city = new City(cityName, country, geocoding.getLat(), geocoding.getLon(), airPollution.getAirQuality(), airPollution.getCo(), airPollution.getNo(), airPollution.getNo2(), airPollution.getO3(), airPollution.getSo2(), airPollution.getPm2_5(), airPollution.getPm10(), airPollution.getNh3());

            // Add the city to the cache
            cityCache.put(city, 30000L);
        }
        return city;
    }

    public HashMap<String, Long> getStats() {
        return cityCache.getStats();
    }

    public HashMap<String, City> getCache() {
        return cityCache.getCache();
    }
}
