package tqs.backend.controller;

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
    public City getAirPollution(@RequestParam String city, @RequestParam String country) {
        return cityService.getCity(city, country);
    }

    @GetMapping("/stats")
    public Object getStats() {
        return cityService.getStats();
    }

    @GetMapping("/cache")
    public Object getCache() {
        return cityService.getCache();
    }
}
