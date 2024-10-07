package org.example.server.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Objects;

@Schema(description = "Информация о погоде")
public class WeatherResponseDTO {
    @Schema(
            description = "имя сенсора",
            example = "TestSensor"
    )
    private String nameSensor;
    @Schema(
            description = "Время сообщения",
            example = "2024-10-06T23:49:22.528Z"
    )
    private LocalDateTime time;
    @Schema(
            description = "Значение температуры",
            example = "25.5"
    )
    private double value;
    @Schema(
            description = "Информация о дожде",
            example = "false"
    )
    private boolean raining;

    public WeatherResponseDTO(String nameSensor, LocalDateTime time, double value, boolean raining) {
        this.nameSensor = nameSensor;
        this.time = time;
        this.value = value;
        this.raining = raining;
    }

    public String getNameSensor() {
        return nameSensor;
    }

    public void setNameSensor(String nameSensor) {
        this.nameSensor = nameSensor;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherResponseDTO that = (WeatherResponseDTO) o;
        return Double.compare(value, that.value) == 0 && raining == that.raining && Objects.equals(nameSensor, that.nameSensor) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameSensor, time, value, raining);
    }
}
