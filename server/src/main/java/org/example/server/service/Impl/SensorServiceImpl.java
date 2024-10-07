package org.example.server.service.Impl;

import org.example.server.dto.SensorResponseDTO;
import org.example.server.entity.SensorEntity;
import org.example.server.exceptions.NameSensorException;
import org.example.server.exceptions.NotFoundException;
import org.example.server.repository.SensorRepository;
import org.example.server.service.SensorService;
import org.example.server.util.convertors.SensorToResponseConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SensorServiceImpl implements SensorService {
    private static final Logger log = LoggerFactory.getLogger(SensorServiceImpl.class);

    @Autowired
    private final SensorRepository sensorRepository;
    @Autowired
    private final SensorToResponseConverter toResponseConverter;

    public SensorServiceImpl(SensorRepository sensorRepository, SensorToResponseConverter toResponseConverter) {
        this.sensorRepository = sensorRepository;
        this.toResponseConverter = toResponseConverter;
    }


    @Override
    @Transactional
    public UUID registration(String name) {
        log.info("Registering new sensor {}", name);
        log.info("Found sensor with name {}", name);
        SensorEntity sensor = sensorRepository.findByName(name);
        log.info("Sensor with name not found {}", name);
        if (sensor == null) {
            log.info("Creating new sensor {}", name);
            sensor = new SensorEntity(name, true);
            log.info("Save new sensor {}", name);
            sensorRepository.save(sensor);
            log.info("Saving new sensor {}", name);
        } else {
            log.error("Sensor with name {} already exists", name);
            throw new NameSensorException("Сенсор с таким именем уже существует, name {} " + name);
        }
        return sensor.getId();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<SensorResponseDTO> getActiveSensors(boolean active) {
        log.info("Getting active sensors");
        List<SensorEntity> sensors = sensorRepository.findAllByActive(active);
        return sensors.stream()
                .map(s -> {
                    log.info("start convert active sensor entity to response id {}", s.getId());
                    return toResponseConverter.convert(s);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SensorEntity getSensor(UUID id) {
        log.info("sensor found with id: " + id);
        SensorEntity entity = sensorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("sensor not found with id: " + id);
                    return new  NotFoundException("Датчик не найден, uuid -> " + id);
                });
        return entity;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setSensorStatus(UUID sensorId, boolean status) {
        log.info("Setting sensor status to {}", status);
        SensorEntity entity = sensorRepository.findById(sensorId)
                .orElseThrow(() -> {
                    log.error("sensor not found  to uuid {}", sensorId);
                    return new  NotFoundException("Датчик не найден, uuid -> " + sensorId);
                });
        entity.setActive(status);
        sensorRepository.save(entity);
        log.info("Sensor status updated to uuid {}", sensorId);
    }
}
