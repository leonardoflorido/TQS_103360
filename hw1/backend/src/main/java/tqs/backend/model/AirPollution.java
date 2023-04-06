package tqs.backend.model;

public class AirPollution {
    private final String airQuality;
    private final double co;
    private final double no;
    private final double no2;
    private final double o3;
    private final double so2;
    private final double pm2_5;
    private final double pm10;
    private final double nh3;

    public AirPollution(int index, double co, double no, double no2, double o3, double so2, double pm2_5, double pm10, double nh3) {
        this.airQuality = calculateAirQuality(index);
        this.co = co;
        this.no = no;
        this.no2 = no2;
        this.o3 = o3;
        this.so2 = so2;
        this.pm2_5 = pm2_5;
        this.pm10 = pm10;
        this.nh3 = nh3;
    }

    private String calculateAirQuality(int index) {
        return switch (index) {
            case 1 -> "Good";
            case 2 -> "Fair";
            case 3 -> "Moderate";
            case 4 -> "Poor";
            case 5 -> "Very Poor";
            default -> null;
        };
    }

    public String getAirQuality() {
        return airQuality;
    }


    public double getCo() {
        return co;
    }


    public double getNo() {
        return no;
    }


    public double getNo2() {
        return no2;
    }


    public double getO3() {
        return o3;
    }


    public double getSo2() {
        return so2;
    }


    public double getPm2_5() {
        return pm2_5;
    }


    public double getPm10() {
        return pm10;
    }


    public double getNh3() {
        return nh3;
    }
}
