package tqs.backend.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.backend.api.AirPollutionApi;
import tqs.backend.api.GeocodingApi;
import tqs.backend.model.AirPollution;
import tqs.backend.model.City;
import tqs.backend.model.Geocoding;
import tqs.backend.service.CityService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CityController.class)
class CityControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CityService service;

    @Test
    @DisplayName("Test the GET /api/air_pollution endpoint with valid city name")
    void whenGetCityByNameAndCountry_thenReturnJsonArray() throws Exception {
        String cityName = "Porto";
        String country = "PT";

        Geocoding geocoding = GeocodingApi.getGeocoding(cityName, country);
        assertNotNull(geocoding);

        AirPollution airPollution = AirPollutionApi.getAirPollution(geocoding.lat(), geocoding.lon());
        assertNotNull(airPollution);

        City porto = new City(cityName, country, geocoding.lat(), geocoding.lon(), airPollution.getAirQuality(), airPollution.getCo(), airPollution.getNo(), airPollution.getNo2(), airPollution.getO3(), airPollution.getSo2(), airPollution.getPm2_5(), airPollution.getPm10(), airPollution.getNh3());

        when(service.getCity(cityName, country)).thenReturn(porto);

        mvc.perform(get("/api/air_pollution?cityName=" + cityName + "&country=" + country)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName", is(cityName)))
                .andExpect(jsonPath("$.country", is(country)));
    }

    @Test
    @DisplayName("Test the GET /api/air_pollution endpoint with invalid city name")
    void whenGetCityByNameAndCountry_thenReturnError() throws Exception {
        String cityName = "New York";
        String country = "PT";

        when(service.getCity(cityName, country)).thenReturn(null);

        mvc.perform(get("/api/air_pollution?cityName=" + cityName + "&country=" + country)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test the GET /api/cache/stats endpoint")
    void whenGetStats_thenReturnJsonArray() throws Exception {
        mvc.perform(get("/api/cache/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test the GET /api/cache/data endpoint")
    void whenGetCache_thenReturnJsonArray() throws Exception {
        mvc.perform(get("/api/cache/data")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
