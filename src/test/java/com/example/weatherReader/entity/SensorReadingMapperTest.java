package com.example.weatherReader.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class SensorReadingMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong("reading_id")).thenReturn(1L);
        when(resultSet.getLong("sensor_id")).thenReturn(2L);
        when(resultSet.getDouble("temperatureCelsius")).thenReturn(25.5);
        when(resultSet.getDouble("humidity")).thenReturn(60.0);
        when(resultSet.getDouble("pressure")).thenReturn(1015.5);
        when(resultSet.getDouble("rain_fall_mm")).thenReturn(5.0);
        when(resultSet.getDouble("wind_speed")).thenReturn(15.0);
        when(resultSet.getString("wind_direction")).thenReturn("N");
        when(resultSet.getTimestamp("reading_time")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getTimestamp("created_at")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getTimestamp("updated_at")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));

        SensorReadingMapper sensorReadingMapper = new SensorReadingMapper();

        SensorReading sensorReading = sensorReadingMapper.mapRow(resultSet, 1);

        assert sensorReading != null;
        assertEquals(1L, sensorReading.getReadingId());
        assertEquals(2L, sensorReading.getSensorId());
        assertEquals(25.5, sensorReading.getTemperatureCelsius());
        assertEquals(60.0, sensorReading.getHumidity());
        assertEquals(1015.5, sensorReading.getPressure());
        assertEquals(5.0, sensorReading.getRainFallMm());
        assertEquals(15.0, sensorReading.getWindSpeed());
        assertEquals("N", sensorReading.getWindDirection());
    }
}
