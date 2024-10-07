package org.example.server.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Schema(description = "Информация о сенсорах")
public class SensorResponseDTO {
    @Schema(
            description = "UUID сенсора",
            example = "9ce694aa-4da8-4e57-b995-9d032e720a5b"
    )
    private UUID id;
    @Schema(
            description = "Имя сенсора",
            example = "TestSensor"
    )
    private String name;
    @Schema(
            description = "Активность сенсора",
            example = "true"
    )
    private boolean isActive;


    public SensorResponseDTO(UUID id, String name, boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorResponseDTO that = (SensorResponseDTO) o;
        return isActive == that.isActive && Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isActive);
    }
}
