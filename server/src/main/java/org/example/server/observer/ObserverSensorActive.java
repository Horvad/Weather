package org.example.server.observer;

import org.example.server.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ObserverSensorActive {
    private static ConcurrentHashMap<String, Timer> mapTimer = new ConcurrentHashMap<>();

    @Autowired
    private final SensorService sensorService;

    @Value("${config.timeActiveSensor}")
    private int time;

    public ObserverSensorActive(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    public void observe(String uuid) {
        if (mapTimer.containsKey(uuid)) {
            mapTimer.get(uuid).cancel();
        }
        mapTimer.put(uuid, new Timer(false));
        mapTimer.get(uuid).schedule(getTimerTask(uuid), time * 60 * 1000);
    }

    private TimerTask getTimerTask(String key) {
        return new TimerTask() {

            @Override
            public void run() {
                sensorService.setSensorStatus(UUID.fromString(key), false);
            }
        };
    }
}
