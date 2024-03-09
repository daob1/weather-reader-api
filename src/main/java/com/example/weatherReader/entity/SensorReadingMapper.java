package com.example.weatherReader.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SensorReadingMapper implements RowMapper<SensorReading> {
    @Override
    public SensorReading mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return SensorReading.builder()
                .readingId(resultSet.getLong("reading_id"))
                .sensorId(resultSet.getLong("sensor_id"))
                .temperatureCelsius(resultSet.getDouble("temperatureCelsius"))
                .humidity(resultSet.getDouble("humidity"))
                .pressure(resultSet.getDouble("pressure"))
                .rainFallMm(resultSet.getDouble("rain_fall_mm"))
                .windSpeed(resultSet.getDouble("wind_speed"))
                .windDirection(resultSet.getString("wind_direction"))
                .readingTime(resultSet.getTimestamp("reading_time"))
                .createdAt(resultSet.getTimestamp("created_at"))
                .updatedAt(resultSet.getTimestamp("updated_at"))
                .build();
    }
}
