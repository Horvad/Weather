package org.example.server.inPoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.server.dto.SensorResponseDTO;
import org.example.server.facade.SensorFacade;
import org.example.server.validation.UuidValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/sensors")
@Tag(name = "Контроллер для обработке запросов от пользователя")
public class UserController {
    @Autowired
    private final SensorFacade facade;

    public UserController(SensorFacade facade) {
        this.facade = facade;
    }

    @Operation(
            summary = "Получение всех активных сенсоров",
            description = "Метод позволяет получить все активные сенсоры"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Запрос выполнен успешно",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SensorResponseDTO.class)))
    )
    @GetMapping
    public ResponseEntity<?> getSensors() {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getActiveSensors());
    }

    @Operation(
            summary = "Получение информации о последних изменениях сенсора",
            description = "Метод позволяет получить информацию о последних изменениях сенсора"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Запрос выполнен успешно",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SensorResponseDTO.class)))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Сенсор не найден",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "не верно ввенед UUID",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping(path = "/{key}/measurements")
    public ResponseEntity<?> getMeasurementsWithSensor(
            @Parameter(description = "UUID сенсора", example = "9ce694aa-4da8-4e57-b995-9d032e720a5b")
            @PathVariable("key") @Valid @UuidValidation String key) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getWeathersWithSensor(UUID.fromString(key)));
    }

    @Operation(
            summary = "Получение информации с активных сенсоров",
            description = "Метод позволяет получить информацию с активных сенсоров"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Выполнено успешно",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SensorResponseDTO.class)))
    )
    @GetMapping(path = "/measurements")
    public ResponseEntity<?> getMeasurements() {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getWeathersWithTime());
    }
}
