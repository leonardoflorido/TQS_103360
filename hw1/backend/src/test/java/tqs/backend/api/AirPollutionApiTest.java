package tqs.backend.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AirPollutionApiTest {

    @Test
    @DisplayName("Test the get method with a valid location")
    public void testGetAirPollution() {
        assertNotNull(AirPollutionApi.getAirPollution(41.1494512, -8.6107884));
    }

    @Test
    @DisplayName("Test the get method with an invalid location")
    public void testGetAirPollutionInvalid() {
        assertThrows(RuntimeException.class, () -> {
            AirPollutionApi.getAirPollution(95, 95);
        });
    }
}
