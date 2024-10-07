package org.example.server.facade.impl;

import org.example.server.dto.CreatedResponseDTO;
import org.example.server.dto.SensorResponseDTO;
import org.example.server.dto.WeatherRequestDTO;
import org.example.server.dto.WeatherResponseDTO;
import org.example.server.exceptions.SensorException;
import org.example.server.facade.SensorFacade;
import org.example.server.observer.ObserverSensorActive;
import org.example.server.service.SensorService;
import org.example.server.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SensorFacadeImpl implements SensorFacade {
    private static final Logger log = LoggerFactory.getLogger(SensorFacadeImpl.class);
    @Autowired
    private final SensorService sensorService;
    @Autowired
    private final WeatherService weatherService;
    @Autowired
    private final ObserverSensorActive observer;

    @Value("${config.maxValue}")
    private int maxValue;
    @Value("${config.minValue}")
    private int minValue;



    public SensorFacadeImpl(SensorService sensorService, WeatherService weatherService, ObserverSensorActive observer) {
        this.sensorService = sensorService;
        this.weatherService = weatherService;
        this.observer = observer;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UUID registration(String name) {
        UUID uuid = sensorService.registration(name);
        observer.observe(uuid.toString());
        return uuid;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CreatedResponseDTO addWeather(UUID key, WeatherRequestDTO weather) {
        validateWeather(key, weather);
        weatherService.saveWeather(key, weather);
        observer.observe(key.toString());
        return new CreatedResponseDTO();
    }

    @Override
    public List<SensorResponseDTO> getActiveSensors() {
        return sensorService.getActiveSensors(true);
    }

    @Override
    public List<WeatherResponseDTO> getWeathersWithSensor(UUID key) {
        return weatherService.getWeatherBySensor(key);
    }

    @Override
    public List<WeatherResponseDTO> getWeathersWithTime() {
        return weatherService.getWeatherByTime();
    }

    private boolean validateWeather(UUID key, WeatherRequestDTO weather){
        if(weather.getValue() == null){
            log.error("Sensor value is null {}", key);
            throw new SensorException("Sensor value is null {} "+ key);
        }
        if(weather.getValue() > maxValue || weather.getValue() < minValue){
            log.error("Sensor value {} is greater than 100 {} ", key);
            throw new SensorException("Sensor value {} is greater than max or min value " + key);
        }
        if(weather.getRaining() == null){
            log.error("Sensor ruining is null {}", key);
            throw new SensorException("Sensor ruining is null " + key);
        }
        return true;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

}
