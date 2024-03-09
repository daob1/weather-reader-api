package com.example.weatherReader.rest;

import com.example.weatherReader.dto.SensorReadingDto;
import com.example.weatherReader.entity.Sensor;
import com.example.weatherReader.services.WeatherSensorService;
import com.example.weatherReader.utils.LocalDateTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@RestController
public class WeatherSensorController {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();

    @Autowired
    private WeatherSensorService weatherSensorService;

    @GetMapping("/v1/sensors")
    public ResponseEntity<String> sensorsVersion1() {
        return ResponseEntity.ok(gson.toJson(weatherSensorService.getSensors()));
    }

    @GetMapping("/latest/sensors")
    public ResponseEntity<String> sensorsLatestVersion() {
        return sensorsVersion1();
    }


    @GetMapping("/v1/sensor")
    public ResponseEntity<String> sensorByIdVersion1(@RequestParam(value = "sensorId", defaultValue = "1") int sensorId) {
        Optional<Sensor> sensorById = weatherSensorService.getSensorById(sensorId);
        return sensorById.map(sensor -> ResponseEntity.ok(gson.toJson(sensor))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/latest/sensor")
    public ResponseEntity<String> sensorByIdLatestVersion(@RequestParam(value = "sensorId", defaultValue = "1") int sensorId) {
        return sensorByIdVersion1(sensorId);
    }

    @GetMapping("/v1/sensorReadings")
    public ResponseEntity<String> sensorReadingsVersion1(@RequestParam(value = "page", defaultValue = "1") @Min(1) int page,
                                                         @RequestParam(value = "pageSize", defaultValue = "15") @Min(1) int pageSize) {
        return ResponseEntity.ok(gson.toJson(weatherSensorService.getSensorReadings(page, pageSize)));
    }

    @GetMapping("/latest/sensorReadings")
    public ResponseEntity<String> sensorReadingsLatest(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "pageSize", defaultValue = "15") int pageSize) {
        return sensorReadingsVersion1(page, pageSize);
    }

    @PostMapping("/v1/sensorReading")
    public ResponseEntity<String> insertSensorReadingVersion1(@RequestBody @Valid SensorReadingDto sensorReadingDto) {
        return ResponseEntity.status(weatherSensorService.addSensorReading(sensorReadingDto)).build();
    }

    @PostMapping("/latest/sensorReading")
    public ResponseEntity<String> insertSensorReadingLatest(@RequestBody @Valid SensorReadingDto sensorReadingDto) {
        return insertSensorReadingVersion1(sensorReadingDto);
    }

    @GetMapping("/v1/averageTemperatureInCountryBetweenTimes/{countryCode}/{dateFrom}/{dateTo}")
    public ResponseEntity<Double> getAverageTemperatureInCountryBetweenTimes(@Parameter(description = "country iso 2 code ",
            example = "IE")
                                                                             @PathVariable @Size(max = 2) String countryCode,
                                                                             @Parameter(description = "UTC transaction date yyyy-MM-dd'T'HH:mm:ss",
                                                                                     example = "2024-01-01T01:59:59")
                                                                             @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateFrom,
                                                                             @Parameter(description = "UTC transaction date yyyy-MM-dd'T'HH:mm:ss",
                                                                                     example = "2024-12-31T01:59:59")
                                                                             @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTo) {
        return ResponseEntity.ok(weatherSensorService.getAverageTemperatureForAllSensorsInDateRangeInCountry(countryCode, dateFrom.toInstant(ZoneOffset.UTC), dateTo.toInstant(ZoneOffset.UTC)));
    }

    @GetMapping("/latest/averageTemperatureInCountryBetweenTimes/{countryCode}/{dateFrom}/{dateTo}")
    public ResponseEntity<Double> getAverageTemperatureInCountryBetweenTimesLatestVersion(
            @Parameter(description = "country iso 2 code ",
                    example = "IE")
            @PathVariable @NotBlank @Size(max = 2) String countryCode,
            @Parameter(description = "UTC transaction date yyyy-MM-dd'T'HH:mm:ss",
                    example = "2024-01-01T01:59:59")
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateFrom,
            @Parameter(description = "UTC transaction date yyyy-MM-dd'T'HH:mm:ss",
                    example = "2024-12-31T01:59:59")
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTo) {
        return getAverageTemperatureInCountryBetweenTimes(countryCode, dateFrom, dateTo);
    }


    @GetMapping("/v1/averageTemperatureBetweenTimes/allSensors/{dateFrom}/{dateTo}")
    public ResponseEntity<Double> getAverageTemperatureBetweenTimesVersion1(@Parameter(description = "UTC transaction date yyyy-MM-dd'T'HH:mm:ss",
            example = "2024-01-01T01:59:59")
                                                                            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateFrom,
                                                                            @Parameter(description = "UTC transaction date yyyy-MM-dd'T'HH:mm:ss",
                                                                                    example = "2024-12-31T01:59:59")
                                                                            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTo) {
        return ResponseEntity.ok(weatherSensorService.getAverageTemperatureForAllSensorsInDateRange(dateFrom.toInstant(ZoneOffset.UTC), dateTo.toInstant(ZoneOffset.UTC)));
    }

    @GetMapping("/latest/averageTemperatureBetweenTimes/allSensors/{dateFrom}/{dateTo}")
    public ResponseEntity<Double> getAverageTemperatureBetweenTimesLatestVersion(
            @Parameter(description = "UTC transaction date yyyy-MM-dd'T'HH:mm:ss",
                    example = "2024-01-01T01:59:59")
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateFrom,
            @Parameter(description = "UTC transaction date yyyy-MM-dd'T'HH:mm:ss",
                    example = "2024-12-31T01:59:59")
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTo) {
        return getAverageTemperatureBetweenTimesVersion1(dateFrom, dateTo);
    }

    @GetMapping("/v1/averageTemperatureBetweenTimes/{sensorId}/{dateFrom}/{dateTo}")
    public ResponseEntity<Double> getAverageTemperatureOnSensorBetweenTimesVersion1(
            @Parameter(example = "1") @PathVariable int sensorId,
            @Parameter(description = "UTC transaction date yyyy-MM-dd'T'HH:mm:ss",
                    example = "2024-01-01T01:59:59")
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateFrom,
            @Parameter(description = "UTC transaction date yyyy-MM-dd'T'HH:mm:ss",
                    example = "2024-12-31T01:59:59")
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTo) {
        return ResponseEntity.ok(weatherSensorService.getAverageTemperatureForSingleSensorInDateRange(sensorId, dateFrom.toInstant(ZoneOffset.UTC), dateTo.toInstant(ZoneOffset.UTC)));
    }

    @GetMapping("/latest/averageTemperatureBetweenTimes/{sensorId}/{dateFrom}/{dateTo}")
    public ResponseEntity<Double> getAverageTemperatureOnSensorBetweenTimesLatestVersion(
            @Parameter(example = "1") @PathVariable int sensorId,
            @Parameter(description = "UTC transaction date yyyy-MM-dd'T'HH:mm:ss",
                    example = "2024-01-01T01:59:59")
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateFrom,
            @Parameter(description = "UTC transaction date yyyy-MM-dd'T'HH:mm:ss",
                    example = "2024-12-31T01:59:59")
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTo) {
        return getAverageTemperatureOnSensorBetweenTimesVersion1(sensorId, dateFrom, dateTo);
    }
}
