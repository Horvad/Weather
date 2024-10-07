package facade;

import org.example.server.dto.SensorResponseDTO;
import org.example.server.dto.WeatherRequestDTO;
import org.example.server.dto.WeatherResponseDTO;
import org.example.server.exceptions.SensorException;
import org.example.server.facade.impl.SensorFacadeImpl;
import org.example.server.observer.ObserverSensorActive;
import org.example.server.service.SensorService;
import org.example.server.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestFacade {
    @Mock
    SensorService sensorService;
    @Mock
    WeatherService weatherService;
    @Mock
    ObserverSensorActive observer;
    @InjectMocks
    SensorFacadeImpl facade;

    @Value("${config.maxValue}")
    private int maxValue;
    @Value("${config.minValue}")
    private int minValue;

    private static String nameValid = "Test";
    private static String nameInvalid = "In";
    private static UUID uuid = UUID.randomUUID();
    private static WeatherRequestDTO weather = new WeatherRequestDTO(
            25, false
    );

    @Test
    public void registration() {
        when(sensorService.registration(nameValid)).thenReturn(uuid);
        UUID getUUID = facade.registration(nameValid);
        assertEquals(uuid,getUUID);
        verify(observer).observe(uuid.toString());
    }

    @Test
    public void addWeather() {
        facade.setMaxValue(100);
        facade.setMaxValue(100);
        facade.addWeather(uuid, weather);
        verify(weatherService).saveWeather(uuid,weather);
        verify(observer).observe(uuid.toString());

        assertThrows(SensorException.class, ()-> facade.addWeather(uuid, new WeatherRequestDTO(-101,false)));
        assertThrows(SensorException.class, ()-> facade.addWeather(uuid, new WeatherRequestDTO(101,false)));

    }

    @Test
    public void getActiveSensors() {
        List<SensorResponseDTO> list = new ArrayList<>();
        list.add(new SensorResponseDTO(UUID.randomUUID(),"test",true));
        when(sensorService.getActiveSensors(true)).thenReturn(list);
        List<SensorResponseDTO> listTest = facade.getActiveSensors();

        for(int i = 0; i<listTest.size(); i++){
            assertEquals(listTest.get(i),(list.get(i)));
        }
    }

    @Test
    public void getWeathersWithSensor() {
        List<WeatherResponseDTO> list = new ArrayList<>();
        list.add(new WeatherResponseDTO("test", LocalDateTime.now(),2,false));
        when(weatherService.getWeatherBySensor(uuid)).thenReturn(list);
        List<WeatherResponseDTO> listTest = facade.getWeathersWithSensor(uuid);
        for(int i = 0; i<listTest.size(); i++){
            assertEquals(listTest.get(i),(list.get(i)));
        }
    }

   @Test
    public void getWeathersWithTime() {
       List<WeatherResponseDTO> list = new ArrayList<>();
       list.add(new WeatherResponseDTO("test", LocalDateTime.now(),2,false));
       when(weatherService.getWeatherByTime()).thenReturn(list);
       List<WeatherResponseDTO> listTest = facade.getWeathersWithSensor(uuid);
       for(int i = 0; i<listTest.size(); i++){
           assertEquals(listTest.get(i),(list.get(i)));
       }
    }
}
