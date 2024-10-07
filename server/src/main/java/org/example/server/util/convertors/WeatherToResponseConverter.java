package org.example.server.util.convertors;

import org.example.server.dto.WeatherResponseDTO;
import org.example.server.entity.WeatherEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WeatherToResponseConverter implements Converter<WeatherEntity, WeatherResponseDTO> {
    @Override
    public WeatherResponseDTO convert(WeatherEntity source) {
        return new WeatherResponseDTO(
                source.getSensor().getName(),
                source.getTime(),
                source.getValue(),
                source.isRaining()
        );
    }
}
