package com.example.weatherReader.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class SensorRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getNString("sensor_Id")).thenReturn("1");
        when(resultSet.getNString("sensor_name")).thenReturn("TestSensor");
        when(resultSet.getNString("sensor_description")).thenReturn("Description");
        when(resultSet.getNString("manufacturer")).thenReturn("Manufacturer");
        when(resultSet.getNString("location_id")).thenReturn("2");
        when(resultSet.getNString("install_date")).thenReturn("2022-03-01 10:30:00");
        when(resultSet.getNString("created_at")).thenReturn("2022-03-01 10:31:00");
        when(resultSet.getNString("updated_at")).thenReturn("2022-03-01 10:32:00");

        SensorRowMapper sensorRowMapper = new SensorRowMapper();

        Sensor sensor = sensorRowMapper.mapRow(resultSet, 1);

        assert sensor != null;
        assertEquals(1, sensor.getId());
        assertEquals("TestSensor", sensor.getName());
        assertEquals("Description", sensor.getSensor_description());
        assertEquals("Manufacturer", sensor.getManufacturer());
        assertEquals(2, sensor.getLocation_id());
        assertEquals(LocalDateTime.parse("2022-03-01 10:30:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), sensor.getInstall_date());
        assertEquals(LocalDateTime.parse("2022-03-01 10:31:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), sensor.getCreated_at());
        assertEquals(LocalDateTime.parse("2022-03-01 10:32:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), sensor.getUpdated_at());
    }
}
