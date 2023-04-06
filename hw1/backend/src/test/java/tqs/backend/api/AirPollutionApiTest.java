package tqs.backend.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AirPollutionApiTest {
    @Test
    @DisplayName("Test the get method with a valid location")
    void testGetAirPollution() {
        assertNotNull(AirPollutionApi.getAirPollution(41.1494512, -8.6107884));
    }

    @Test
    @DisplayName("Test the get method with an invalid location")
    void testGetAirPollutionInvalid() {
        assertThrows(RuntimeException.class, () -> {
            AirPollutionApi.getAirPollution(95, 95);
        });
    }
}
