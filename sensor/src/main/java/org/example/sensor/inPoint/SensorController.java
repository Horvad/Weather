package org.example.sensor.inPoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.sensor.exeptions.ExceptionResponse;
import org.example.sensor.service.SensorService;
import org.example.sensor.validation.NameValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.logging.Logger;

@RestController
@Tag(name = "Контроллер для управления сенсором")
public class SensorController {
    private static final Logger logger = Logger.getLogger(SensorController.class.getName());

    @Autowired
    private final SensorService service;

    public SensorController(SensorService service) {
        this.service = service;
    }

    @Operation(
            summary = "Регистрация сенсора на сервере",
            description = "Данный метод регистрирует сенсора и начинает отправку на сервер"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Запрос выполнен успешно",
            content = @Content(schema = @Schema(implementation = UUID.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Запрос выполнен не корректно",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
    )
    @GetMapping(path = "/registration")
    public ResponseEntity<?> registration(
            @Parameter(example = "TestSensor")
            @RequestHeader @Valid @NameValidation String name
    ) {
        logger.info("start registration sensor");
        service.registration(name);
        logger.info("end registration sensor");
        service.startSending();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
