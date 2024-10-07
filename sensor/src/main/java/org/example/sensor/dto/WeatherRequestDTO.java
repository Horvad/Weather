package org.example.sensor.dto;

public class WeatherRequestDTO {
    private Double value;
    private Boolean raining;

    public WeatherRequestDTO() {
    }

    public WeatherRequestDTO(double value, boolean raining) {
        this.value = value;
        this.raining = raining;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }
}
