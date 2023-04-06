package tqs.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CityServiceTest {
    private CityService cityService;

    @BeforeEach
    void setUp() {
        cityService = new CityService();
    }

    @Test
    @DisplayName("Test the get method")
    void getCityTest() {
        assertNotNull(cityService.getCity("Porto", "PT"));
    }

    @Test
    @DisplayName("Test the get method with a non-existent city")
    void getCityNotFoundTest() {
        assertThrows(RuntimeException.class, () -> {
            cityService.getCity("Non-existent city", "Non-existent country");
        });
    }

    @Test
    @DisplayName("Test the getCache method")
    void getCacheTest() {
        assertNotNull(cityService.getCache());
    }

    @Test
    @DisplayName("Test the getStats method")
    void getStatsTest() {
        assertNotNull(cityService.getStats());
    }
}
