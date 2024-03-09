package com.example.weatherReader.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record SensorReadingDto(@Schema(example="1")
                               @NotNull(message = "sensorId cannot be empty") Long sensorId,
                               Double temperatureCelsius,
                               Double humidity,
                               Double pressure,
                               Double rainFallMm,
                               Double windSpeed,
                               String windDirection,
                               @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
                               Instant readingTime) {
    public SensorReadingDto{
        if(sensorId == null || sensorId < 1){
            throw new IllegalArgumentException("sensorId cannot be null or less than 1");
        }
    }
}
