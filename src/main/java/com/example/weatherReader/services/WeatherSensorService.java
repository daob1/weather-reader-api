package com.example.weatherReader.services;

import com.example.weatherReader.dto.SensorReadingDto;
import com.example.weatherReader.entity.Sensor;
import com.example.weatherReader.entity.SensorReading;
import com.example.weatherReader.entity.SensorReadingMapper;
import com.example.weatherReader.entity.SensorRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatusCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WeatherSensorService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Sensor> getSensors() {
        return jdbcTemplate.query("SELECT * FROM sensors ORDER BY sensor_Id;", new SensorRowMapper());
    }

    public Optional<Sensor> getSensorById(int sensorId) {
        log.debug("Getting sensor {} by Id.", sensorId);
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(String.format("SELECT * FROM sensors WHERE sensor_id=%d LIMIT 1;", sensorId), new SensorRowMapper())
            );
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.debug("No sensor found with Id {}", sensorId);
            return Optional.empty();
        }

    }

    public List<SensorReading> getSensorReadings(int pageNum, int pageSize) {
        int offset = calculateOffset(pageNum, pageSize);
        log.debug(String.format("SELECT * FROM readings ORDER BY reading_time DESC LIMIT %d OFFSET %d;", pageSize, offset));
        return jdbcTemplate.query(String.format("SELECT * FROM readings ORDER BY reading_time DESC LIMIT %d OFFSET %d;", pageSize, offset), new SensorReadingMapper());
    }

    public HttpStatusCode addSensorReading(SensorReadingDto sensorReading) {

        String sqlInsertStatement = """
                INSERT INTO readings (sensor_id, temperatureCelsius, humidity, pressure, rain_fall_mm, wind_speed, wind_direction, reading_time)
                VALUES (?,?,?,?,?,?,?,?);
                """;
        try {
            jdbcTemplate.update(sqlInsertStatement, sensorReading.sensorId(), sensorReading.temperatureCelsius(),
                    sensorReading.humidity(), sensorReading.pressure(), sensorReading.rainFallMm(), sensorReading.windSpeed(),
                    sensorReading.windDirection(), sensorReading.readingTime());
            return HttpStatusCode.valueOf(201);
        } catch (DataAccessException dataAccessException) {
            log.error(String.format("Something went wrong inserting using statement [%s]", sqlInsertStatement));
            return HttpStatusCode.valueOf(500);
        }

    }

    public Double getAverageTemperatureForAllSensorsInDateRangeInCountry(String country_iso_2_code, Instant dateFromInUTC, Instant dateToInUTC) {
        String sqlQuery = """
                SELECT AVG(r.temperatureCelsius)
                FROM readings r
                JOIN sensors s ON r.sensor_id = s.sensor_id
                JOIN locations l ON s.location_id = l.location_id
                WHERE l.country_iso_2_code = '%s' AND r.reading_time BETWEEN '%s' AND '%s' ;
                """;
        String sqlQueryParameter = String.format(sqlQuery, country_iso_2_code, dateFromInUTC, dateToInUTC);
        return jdbcTemplate.queryForObject(sqlQueryParameter, Double.class);
    }

    public Double getAverageTemperatureForAllSensorsInDateRange(Instant dateFromInUTC, Instant dateToInUTC) {
        String sqlQuery = """
                SELECT AVG(r.temperatureCelsius)
                FROM readings r
                WHERE r.reading_time BETWEEN '%s' AND '%s' ;
                """;
        String sqlQueryParameter = String.format(sqlQuery, dateFromInUTC, dateToInUTC);
        return jdbcTemplate.queryForObject(sqlQueryParameter, Double.class);
    }

    public Double getAverageTemperatureForSingleSensorInDateRange(Integer sensorId, Instant dateFromInUTC, Instant dateToInUTC) {
        String sqlQuery = """
                SELECT AVG(r.temperatureCelsius)
                FROM readings r
                WHERE r.sensor_id = '%s' AND r.reading_time BETWEEN '%s' AND '%s' ;
                """;
        String sqlQueryParameter = String.format(sqlQuery, sensorId, dateFromInUTC, dateToInUTC);
        return jdbcTemplate.queryForObject(sqlQueryParameter, Double.class);
    }

    // Visible for testing
    static int calculateOffset(int pageNum, int pageSize) {
        if (pageNum <= 1) {
            return 0;
        } else {
            int page = Math.abs(pageNum);
            return Math.multiplyExact((page - 1), Math.abs(pageSize));
        }
    }
}
