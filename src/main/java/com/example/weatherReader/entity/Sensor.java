package com.example.weatherReader.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Builder
@Data
@Entity
public class Sensor {

    @Id
    Integer id;

    String name;

    String sensor_description;

    String manufacturer;

    LocalDateTime install_date;

    @NotNull
    Integer location_id;

    @CreatedDate
    LocalDateTime created_at;

    @LastModifiedDate
    LocalDateTime updated_at;

    public Sensor(Integer id,
                  String name,
                  String sensor_description,
                  String manufacturer,
                  LocalDateTime install_date,
                  Integer location_id,
                  LocalDateTime created_at,
                  LocalDateTime updated_at){
        this.id = id;
        this.name = name;
        this.sensor_description = sensor_description;
        this.manufacturer = manufacturer;
        this.install_date = install_date;
        this.location_id = location_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
