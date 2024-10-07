package org.example.server.util.convertors;

import org.example.server.dto.WeatherRequestDTO;
import org.example.server.entity.WeatherEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WeatherToEntityConverter implements Converter<WeatherRequestDTO, WeatherEntity>{
    @Override
    public WeatherEntity convert(WeatherRequestDTO source) {
        return new WeatherEntity(
                LocalDateTime.now(),
                source.getValue(),
                source.getRaining()
        );
    }
}
