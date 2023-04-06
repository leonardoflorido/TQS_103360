package tqs.backend.airquality;

import org.junit.jupiter.api.Test;
import tqs.backend.model.AirPollution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AirQualityTest {
    @Test
    void testCalculateAirQuality() {
        AirPollution ap = new AirPollution(1, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0);
        assertEquals("Good", ap.getAirQuality());

        ap = new AirPollution(2, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0);
        assertEquals("Fair", ap.getAirQuality());

        ap = new AirPollution(3, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0);
        assertEquals("Moderate", ap.getAirQuality());

        ap = new AirPollution(4, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0);
        assertEquals("Poor", ap.getAirQuality());

        ap = new AirPollution(5, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0);
        assertEquals("Very Poor", ap.getAirQuality());

        ap = new AirPollution(6, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0);
        assertNull(ap.getAirQuality());

        ap = new AirPollution(0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0);
        assertNull(ap.getAirQuality());
    }
}
