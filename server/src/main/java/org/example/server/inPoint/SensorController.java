package org.example.server.inPoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.server.dto.CreatedResponseDTO;
import org.example.server.dto.WeatherRequestDTO;
import org.example.server.exceptions.ExceptionResponse;
import org.example.server.exceptions.NameSensorException;
import org.example.server.facade.SensorFacade;
import org.example.server.validation.NameValidation;
import org.example.server.validation.UuidValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/sensors")
@Tag(name = "Контроллер для обработки запросов от сенсора")
public class SensorController {
    @Autowired
    private final SensorFacade facade;

    public SensorController(SensorFacade facade) {
        this.facade = facade;
    }

    @Operation(
            summary = "Регистрация сенсора на сервере",
            description = "Данный метод позволяет зарегестрировать сенсор на сервера и получить UUID для дальнейшей работы"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Запрос выполнен успешно",
            content = @Content(schema = @Schema(
                    implementation = UUID.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Ошибка с совпадением имени или  ввода невалидных данных",
            content = @Content(schema = @Schema(
                    implementation = ExceptionResponse.class
            ))
    )
    @PostMapping(path = "/registration")
    public ResponseEntity<?> registration(
            @Parameter(description = "Имя сенсора", example = "TestSensor")
            @RequestHeader @Valid @NameValidation String name
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.registration(name));
    }

    @Operation(
            summary = "Получение данных о погоде с сенсора",
            description = "Данный метод позволяет получать данные о погоде с сенсора"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Данные успешно сохранены",
            content = @Content(schema = @Schema(
                    implementation = CreatedResponseDTO.class
            ))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Не верно введен UUID или погода",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
    )
    @ApiResponse(
            responseCode = "409",
            description = "Не верно получены данные от сенсора или сенсор перешел в состояние неактивен",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "UUID сенсора не найден",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
    )
    @PostMapping(path = "/{key}/measurements", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> measurements(
            @Parameter(description = "UUID сенсора", example = "9ce694aa-4da8-4e57-b995-9d032e720a5b")
            @PathVariable @Valid @UuidValidation String key,
            @RequestBody WeatherRequestDTO weather
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.addWeather(UUID.fromString(key), weather));
    }
}
