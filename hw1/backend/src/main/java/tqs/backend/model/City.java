package tqs.backend.model;

public record City(String cityName, String country, double lat, double lon, String airQuality, double co, double no,
                   double no2, double o3, double so2, double pm2_5, double pm10, double nh3) {
}
