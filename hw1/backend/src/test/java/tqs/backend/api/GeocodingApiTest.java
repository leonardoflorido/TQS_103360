package tqs.backend.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tqs.backend.model.Geocoding;

import static org.junit.jupiter.api.Assertions.*;

public class GeocodingApiTest {
    @Test
    @DisplayName("Test the get method with a valid city")
    public void getCityTest() {
        Geocoding geocoding = new Geocoding(41.1494512, -8.6107884);

        Geocoding geocodingApi = GeocodingApi.getGeocoding("Porto", "PT");
        assertNotNull(geocodingApi);

        assertEquals(geocoding.getLat(), geocodingApi.getLat());
        assertEquals(geocoding.getLon(), geocodingApi.getLon());
    }

    @Test
    @DisplayName("Test the get method with a non-existent city")
    public void getCityNotFoundTest() {
        assertNull(GeocodingApi.getGeocoding("Non-existent city", "Non-existent country"));
    }
}
