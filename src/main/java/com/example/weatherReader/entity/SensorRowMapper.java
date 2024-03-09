package com.example.weatherReader.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SensorRowMapper implements RowMapper<Sensor> {

    @Override
    public Sensor mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Sensor.builder()
                .id(Integer.valueOf(rs.getNString("sensor_Id")))
                .name(rs.getNString("sensor_name"))
                .sensor_description(rs.getNString("sensor_description"))
                .manufacturer(rs.getNString("manufacturer"))
                .location_id(Integer.valueOf(rs.getNString("location_id")))
                .install_date(LocalDateTime.parse(rs.getNString("install_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .created_at(LocalDateTime.parse(rs.getNString("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .updated_at(LocalDateTime.parse(rs.getNString("updated_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
