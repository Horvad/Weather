package org.example.server.util.convertors;

import org.example.server.dto.SensorResponseDTO;
import org.example.server.entity.SensorEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SensorToResponseConverter implements Converter<SensorEntity, SensorResponseDTO> {
    @Override
    public SensorResponseDTO convert(SensorEntity source) {
        return new SensorResponseDTO(
                source.getId(),
                source.getName(),
                source.isActive()
        );
    }
}
