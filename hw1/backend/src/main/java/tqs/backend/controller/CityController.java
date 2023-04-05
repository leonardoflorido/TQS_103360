package tqs.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tqs.backend.model.City;
import tqs.backend.service.CityService;

@RestController
@RequestMapping("/api")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/air_pollution")
    public ResponseEntity<City> getAirPollution(@RequestParam String cityName, @RequestParam String country) {
        City city = cityService.getCity(cityName, country);

        if (city == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(city);
    }

    @GetMapping("/cache/stats")
    public Object getStats() {
        return cityService.getStats();
    }

    @GetMapping("/cache/data")
    public Object getCache() {
        return cityService.getCache();
    }
}
