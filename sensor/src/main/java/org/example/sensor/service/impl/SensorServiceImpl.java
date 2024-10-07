package org.example.sensor.service.impl;

import feign.FeignException;
import jakarta.annotation.PreDestroy;
import org.example.sensor.dto.WeatherRequestDTO;
import org.example.sensor.exeptions.FeignExceptionBadRequest;
import org.example.sensor.service.FeignClientServer;
import org.example.sensor.service.SensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

@Service
public class SensorServiceImpl implements SensorService {
    private static final Logger logger = LoggerFactory.getLogger(SensorServiceImpl.class);
    private static UUID uuid;

    @Autowired
    private FeignClientServer feignClient;

    private AtomicBoolean running = new AtomicBoolean(true);

    public SensorServiceImpl(FeignClientServer feignClient) {
        this.feignClient = feignClient;
    }

    @PreDestroy
    public void stop() {
        running.set(false);
        Thread.currentThread().interrupt();
    }

    @Override
    public void registration(String name) {
        try {
            running.set(false);
            uuid = feignClient.registration(name);
            running.set(true);
        } catch (FeignException e) {
            if(e.status()>=400 || e.status()<500)
                throw new FeignExceptionBadRequest(e.status(), e.getMessage());
            throw new RuntimeException("Внутрення обибка сервера");
        }
    }

    @Async("TaskExecutorSend")
    @Override
    public void startSending() {
        while (running.get()) {
            double temp = getTemperature();
            boolean ruining = getRuining();
            logger.info("sending message to server: " + uuid.toString() + ", " + temp + ", " + ruining);
            logger.info(" " + temp + " " +  ruining);
            try {
                feignClient.measurements(uuid.toString(),
                        new WeatherRequestDTO(
                                temp,
                                ruining
                        ));
            } catch (FeignException e) {
                logger.error(e.getMessage());
                if(e.status()==400 || e.status()==404) {
                    throw new RuntimeException("Ошибка отправки данных");
                }
            }
            logger.info("send message to server: " + uuid.toString());
            try {
                sleep(getSleepSeconds());
                logger.info("sleep " + getSleepSeconds());
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    private double getTemperature() {
        return 100 - Math.random() * 200;
    }

    private boolean getRuining() {
        return Math.random() > 0.2;
    }

    private int getSleepSeconds(){
        return ((int) (3 + Math.random()*12))*1000;
    }
}
