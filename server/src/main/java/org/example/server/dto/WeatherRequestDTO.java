package org.example.server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jfr.Description;

@Schema(description = "Сущность для получения погоды")
public class WeatherRequestDTO {
    @Schema(
            description = "Состояние температуры",
            example = "21.1"
    )
    private Double value;
    @Schema(
            description = "Состояние дождя",
            example = "false"
    )
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
