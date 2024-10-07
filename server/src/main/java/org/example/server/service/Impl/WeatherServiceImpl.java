package org.example.server.service.Impl;

import org.example.server.dto.WeatherRequestDTO;
import org.example.server.dto.WeatherResponseDTO;
import org.example.server.entity.WeatherEntity;
import org.example.server.exceptions.DeactivateException;
import org.example.server.exceptions.NotFoundException;
import org.example.server.repository.WeatherRepository;
import org.example.server.service.SensorService;
import org.example.server.service.WeatherService;
import org.example.server.util.convertors.WeatherToEntityConverter;
import org.example.server.util.convertors.WeatherToResponseConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.NotActiveException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);
    @Autowired
    private final WeatherRepository weatherRepository;
    @Autowired
    private final SensorService sensorService;
    @Autowired
    private final WeatherToResponseConverter toResponseConverter;
    @Autowired
    private final WeatherToEntityConverter toEntityConverter;

    @Value("${config.weatherTime}")
    private int timeWeather;
    @Value("${config.countWeathers}")
    private int countWeathers;

    public WeatherServiceImpl(WeatherRepository weatherRepository, SensorService sensorService, WeatherToResponseConverter toResponseConverter, WeatherToEntityConverter toEntityConverter) {
        this.weatherRepository = weatherRepository;
        this.sensorService = sensorService;
        this.toResponseConverter = toResponseConverter;
        this.toEntityConverter = toEntityConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeatherResponseDTO> getWeatherBySensor(UUID key) {
        log.info("Get all weather by sensorId {}", key);
        Pageable page = PageRequest.of(0, countWeathers);
        Page<WeatherEntity> entities = weatherRepository.findBySensorId(page, key);
        log.info("getting all entities");
        List<WeatherResponseDTO> dtos = entities.getContent().stream()
                .map(s -> {
                    log.info("convert entity {}", s.getId());
                    return toResponseConverter.convert(s);
                })
                .collect(Collectors.toList());
        log.info("return dtos");
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeatherResponseDTO> getWeatherByTime() {
        log.info("Get all weather by time");
        LocalDateTime timeSearch = LocalDateTime.now().minusMinutes(timeWeather);
        log.info("time search {}", timeSearch);
        List<WeatherEntity> entities = weatherRepository.findByTimeAfter(timeSearch);
        log.info("getting all entities");
        List<WeatherResponseDTO> dtos = entities.stream()
                .map(s -> {
                    log.info("convert entity {}", s.getId());
                    return toResponseConverter.convert(s);
                })
                .collect(Collectors.toList());
        log.info("return dtos");
        return dtos;
    }

    @Override
    public void saveWeather(UUID key, WeatherRequestDTO weather) {
        log.info("Save weather to {}", key);
        WeatherEntity weatherEntity = toEntityConverter.convert(weather);
        weatherEntity.setSensor(sensorService.getSensor(key));
        if(weatherEntity.getSensor() == null) {
            log.error("Sensor not found {}", key);
            throw new NotFoundException("Sensor not found");
        }
        if(!weatherEntity.getSensor().isActive()){
            log.error("Sensor is not active {}", key);
            throw new DeactivateException("Sensor is not active");
        }
        weatherRepository.save(weatherEntity);
        log.info("Saving weather to {}", key);
    }
}
