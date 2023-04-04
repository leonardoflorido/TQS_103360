package tqs.backend.cache;

import tqs.backend.model.City;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CityCache {
    private static final Logger LOGGER = Logger.getLogger(CityCache.class.getName());
    private final HashMap<String, CacheEntry> cache = new HashMap<>();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private long requestCount = 0;
    private long hitCount = 0;

    public CityCache() {
        startExpiryChecker();
    }

    public void put(City city, long timeToLive) {
        cache.put(city.getCityName(), new CacheEntry(city, timeToLive));
    }

    public City get(String cityName) {
        requestCount++;
        CacheEntry entry = cache.get(cityName);
        if (entry == null) {
            hitCount++;
        } else if (entry.isExpired()) {
            cache.remove(cityName);
            LOGGER.log(Level.INFO, "Removed expired entry for city: " + cityName);
            return null;
        } else {
            entry.resetExpirationTime();
        }
        return entry != null ? entry.getCity() : null;
    }

    public HashMap<String, Long> getStats() {
        HashMap<String, Long> stats = new HashMap<>();
        stats.put("requestCount", requestCount);
        stats.put("hitCount", hitCount);
        return stats;
    }

    public HashMap<String, City> getCache() {
        HashMap<String, City> result = new HashMap<>();
        for (HashMap.Entry<String, CacheEntry> entry : cache.entrySet()) {
            String cityName = entry.getKey();
            CacheEntry cacheEntry = entry.getValue();
            if (!cacheEntry.isExpired()) {
                result.put(cityName, cacheEntry.getCity());
            }
        }
        return result;
    }

    private void startExpiryChecker() {
        executor.scheduleAtFixedRate(() -> {
            int initialSize = cache.size();
            cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
            int finalSize = cache.size();
            LOGGER.log(Level.INFO, "Removed " + (initialSize - finalSize) + " expired cache entries");
        }, 0, 1, TimeUnit.MINUTES);
    }

    private static class CacheEntry {
        private final City city;
        private long expirationTime;

        public CacheEntry(City city, long timeToLive) {
            this.city = city;
            this.expirationTime = System.currentTimeMillis() + timeToLive;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }

        public City getCity() {
            return city;
        }

        public void resetExpirationTime() {
            this.expirationTime = System.currentTimeMillis() + expirationTime - System.currentTimeMillis();
        }
    }
}
