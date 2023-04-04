package tqs.backend.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tqs.backend.model.City;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CityCacheTest {
    private static CityCache cityCache;

    @BeforeEach
    public void setUp() {
        cityCache = new CityCache();
    }

    @Test
    @DisplayName("Test the put method")
    public void putTest() {
        City city = new City("Porto", "PT", 41.1494512, -8.6107884, "Fair", 233.65, 1.34, 5.01, 83.69, 2.98, 2.39, 3.21, 3.96);
        cityCache.put(city, 30000L);

        assertEquals(1, cityCache.getCache().size());
    }

    @Test
    @DisplayName("Test the get method")
    public void getTest() {
        City city = new City("Porto", "PT", 41.1494512, -8.6107884, "Fair", 233.65, 1.34, 5.01, 83.69, 2.98, 2.39, 3.21, 3.96);
        cityCache.put(city, 30000L);

        assertEquals(city, cityCache.get("Porto"));
    }

    @Test
    @DisplayName("Test the expiration of the cache")
    public void expirationTest() throws InterruptedException {
        City city = new City("Porto", "PT", 41.1494512, -8.6107884, "Fair", 233.65, 1.34, 5.01, 83.69, 2.98, 2.39, 3.21, 3.96);
        cityCache.put(city, 1000L);

        Thread.sleep(2000L);

        assertNull(cityCache.get("Porto"));
    }

    @Test
    @DisplayName("Test the not expiration of the cache")
    public void notExpirationTest() throws InterruptedException {
        City city = new City("Porto", "PT", 41.1494512, -8.6107884, "Fair", 233.65, 1.34, 5.01, 83.69, 2.98, 2.39, 3.21, 3.96);
        cityCache.put(city, 30000L);

        Thread.sleep(2000L);

        assertEquals(city, cityCache.get("Porto"));
    }

    @Test
    @DisplayName("Test the stats of the cache")
    public void getStatsTest() {
        City city = new City("Porto", "PT", 41.1494512, -8.6107884, "Fair", 233.65, 1.34, 5.01, 83.69, 2.98, 2.39, 3.21, 3.96);
        cityCache.put(city, 30000L);

        cityCache.get("Porto");
        cityCache.get("Aveiro");

        assertEquals(2, cityCache.getStats().get("requestCount"));
        assertEquals(1, cityCache.getStats().get("hitCount"));
    }

    @Test
    @DisplayName("Test the cache size")
    public void getCacheSizeTest() {
        City porto = new City("Porto", "PT", 41.1494512, -8.6107884, "Fair", 233.65, 1.34, 5.01, 83.69, 2.98, 2.39, 3.21, 3.96);
        cityCache.put(porto, 30000L);

        City aveiro = new City("Aveiro", "PT", 40.6442701, -8.6455394, "Fair", 233.65, 1.34, 5.01, 83.69, 2.98, 2.39, 3.21, 3.96);
        cityCache.put(aveiro, 30000L);

        assertEquals(2, cityCache.getCache().size());
    }
}
