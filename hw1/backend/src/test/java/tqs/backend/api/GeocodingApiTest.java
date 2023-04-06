package tqs.backend.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tqs.backend.model.Geocoding;

import static org.junit.jupiter.api.Assertions.*;

class GeocodingApiTest {
    @Test
    @DisplayName("Test the get method with a valid city")
    void getCityTest() {
        Geocoding geocoding = new Geocoding(41.1494512, -8.6107884);

        Geocoding geocodingFromApi = GeocodingApi.getGeocoding("Porto", "PT");
        assertNotNull(geocodingFromApi);

        assertEquals(geocoding.lat(), geocodingFromApi.lat());
        assertEquals(geocoding.lon(), geocodingFromApi.lon());
    }

    @Test
    @DisplayName("Test the get method with a non-existent city")
    void getCityNotFoundTest() {
        assertNull(GeocodingApi.getGeocoding("Non-existent city", "Non-existent country"));
    }
}
