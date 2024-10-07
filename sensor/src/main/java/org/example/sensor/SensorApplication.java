package org.example.sensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@SpringBootApplication
//@EnableAsync
public class SensorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensorApplication.class, args);
    }

}
