package com.example.weatherReader.services;

import com.example.weatherReader.dto.SensorReadingDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatusCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;

@ExtendWith(SpringExtension.class)
public class WeatherSensorServiceTest {

    @Mock
    JdbcTemplate jdbcTemplate;
    @InjectMocks
    WeatherSensorService weatherSensorService;

    @Test
    void addSensorReadingTest() {
        SensorReadingDto sensorReadingDto =
                new SensorReadingDto(100L,
                        15.1,
                        5.2,
                        1234.5,
                        10.0,
                        5.0,
                        "S",
                        Instant.now()
                );

        HttpStatusCode httpStatusCode = weatherSensorService.addSensorReading(sensorReadingDto);
        assert HttpStatusCode.valueOf(201).equals(httpStatusCode);

    }
}