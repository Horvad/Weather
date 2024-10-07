package org.example.server.facade;

import org.example.server.dto.CreatedResponseDTO;
import org.example.server.dto.SensorResponseDTO;
import org.example.server.dto.WeatherRequestDTO;
import org.example.server.dto.WeatherResponseDTO;

import java.util.List;
import java.util.UUID;

public interface SensorFacade {
    UUID registration(String name);

    CreatedResponseDTO addWeather(UUID key, WeatherRequestDTO weather);

    List<SensorResponseDTO> getActiveSensors();

    List<WeatherResponseDTO> getWeathersWithSensor(UUID key);

    List<WeatherResponseDTO> getWeathersWithTime();
}
