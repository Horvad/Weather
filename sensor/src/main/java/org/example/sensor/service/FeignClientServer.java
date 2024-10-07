package org.example.sensor.service;

import org.example.sensor.dto.WeatherRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "server")
public interface FeignClientServer {

    @PostMapping(value = "/sensors/registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UUID registration(@RequestHeader String name);

    @PostMapping(value = "/sensors/{key}/measurements", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void measurements(
            @PathVariable String key,
            @RequestBody WeatherRequestDTO weather);
}
