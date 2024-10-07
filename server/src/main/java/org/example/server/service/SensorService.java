package org.example.server.service;

import org.example.server.dto.SensorResponseDTO;
import org.example.server.entity.SensorEntity;

import java.util.List;
import java.util.UUID;

public interface SensorService {
    UUID registration(String name);

    List<SensorResponseDTO> getActiveSensors(boolean active);

    SensorEntity getSensor(UUID id);

    void setSensorStatus(UUID sensorId, boolean status);
}
