package org.example.server.service;

import org.example.server.dto.WeatherRequestDTO;
import org.example.server.dto.WeatherResponseDTO;

import java.util.List;
import java.util.UUID;

public interface WeatherService {
    List<WeatherResponseDTO> getWeatherBySensor(UUID key);
    List<WeatherResponseDTO> getWeatherByTime();
    void saveWeather(UUID key, WeatherRequestDTO weather);
}
