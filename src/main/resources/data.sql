DROP TABLE IF EXISTS `sensors`;
DROP TABLE IF EXISTS `locations`;
DROP TABLE IF EXISTS `readings`;

CREATE TABLE sensors (
    sensor_id INT PRIMARY KEY AUTO_INCREMENT,
    sensor_name VARCHAR(255) NOT NULL,
    sensor_description TEXT,
    manufacturer VARCHAR(100),
    install_date TIMESTAMP(3),
    location_id INT NOT NULL, -- Foreign key to the locations table
    created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP AT TIME ZONE 'UTC',
    updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP AT TIME ZONE 'UTC' ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE locations (
    location_id INT PRIMARY KEY AUTO_INCREMENT,
    location_name VARCHAR(255) NOT NULL,
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    address VARCHAR(255),
    country_iso_2_code VARCHAR(255),
    created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP AT TIME ZONE 'UTC',
    updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP AT TIME ZONE 'UTC' ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE readings (
    reading_id INT PRIMARY KEY AUTO_INCREMENT,    -- ToDo plan partitioning strategy before moving to production
    sensor_id INT NOT NULL, -- Foreign key to the sensors table
    temperatureCelsius DECIMAL(5, 2),
    humidity DECIMAL(5, 2),
    pressure DECIMAL(7, 2),
    rain_fall_mm DECIMAL(7, 2),
    wind_speed DECIMAL(6, 2),
    wind_direction VARCHAR(20),
    reading_time TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP AT TIME ZONE 'UTC',  -- in case there is a delay between sensor reading and date being input to the API
    created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP AT TIME ZONE 'UTC',
    updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert data into locations table
INSERT INTO locations (location_name, latitude, longitude, address, country_iso_2_code)
VALUES
    ('Location 1', 40.7128, -74.0060, '123 Cork Street', 'IE'),
    ('Location 2', 64.0522, -28.2437, '456 Dublin Street', 'IE'),
    ('Location 3', 34.0522, -10.2437, '456 BallyMac Street', 'IE'),
    ('Location 4', 4.0522, -115.2437, '456 RathDuffy Street', 'IE'),
    ('Location 5', 78.0522, -118.5557, '456 The Eiffel Tower Street', 'FR'),
    ('Location 6', 36.0522, -112.2437, '333 Macron Street', 'FR'),
    ('Location 7', 24.0522, -100.2437, '456 Osaka Street', 'JP'),
    ('Location 8', 84.0522, -138.2437, '555 Hokkaido Street', 'JP');

-- Insert data into sensors table
INSERT INTO sensors (sensor_name, sensor_description, manufacturer, install_date, location_id)
VALUES
    ('Sensor 1', 'Outdoor climate monitor', 'WeatherTech', '2023-01-15 06:01:01', 1),
    ('Sensor 2', 'Indoor climate monitor', 'ClimateCorp', '2023-02-20 06:01:01', 2),
    ('Sensor 3', 'Outdoor climate monitor', 'ClimateCorp', '2023-02-20 06:01:01', 3),
    ('Sensor 4', 'Outdoor climate monitor', 'ClimateCorp', '2023-02-20 06:01:01', 4),
    ('Sensor 5', 'Outdoor climate monitor', 'ClimateCorp', '2023-02-20 06:01:01', 5),
    ('Sensor 6', 'Outdoor climate monitor', 'ClimateCorp', '2023-02-20 06:01:01', 6),
    ('Sensor 7', 'Outdoor climate monitor', 'ClimateCorp', '2023-02-20 06:01:01', 7),
    ('Sensor 8', 'Outdoor climate monitor', 'ClimateCorp', '2023-02-20 06:01:01', 8);

-- Insert data into readings table
INSERT INTO readings (sensor_id, temperatureCelsius, humidity, pressure, rain_fall_mm, wind_speed, wind_direction, reading_time)
VALUES
  (1, 25.5, 60.2, 1015.3, 5.7, 10.5, 'NE', '2024-03-04 08:00:00.123'),
  (1, 22.3, 55.8, 1012.1, 3.2, 8.2, 'SW', '2024-03-04 09:30:00.456'),
  (1, 23.8, 58.0, 1013.5, 2.5, 9.1, 'NW', '2024-03-04 10:45:00.789'),
  (2, 26.7, 62.5, 1014.8, 8.9, 12.3, 'SE', '2024-03-04 12:15:00.987'),
  (2, 21.1, 50.5, 1011.7, 6.4, 7.8, 'E', '2024-03-04 14:00:00.234'),
  (2, 24.6, 56.3, 1013.2, 4.1, 11.0, 'N', '2024-03-04 15:30:00.567'),
  (3, 28.2, 65.7, 1016.5, 7.3, 14.8, 'S', '2024-03-04 17:00:00.876'),
  (3, 20.5, 48.9, 1010.3, 1.8, 6.5, 'W', '2024-03-04 18:45:00.345'),
  (4, 22.9, 53.2, 1012.9, 3.9, 9.7, 'NE', '2024-03-04 20:20:00.654'),
  (5, 27.4, 60.8, 1015.7, 6.2, 13.2, 'SW', '2024-03-04 22:00:00.789'),
  (5, 19.8, 46.5, 1009.6, 2.2, 8.1, 'NW', '2024-03-05 01:30:00.123'),
  (5, 25.1, 55.0, 1014.2, 4.8, 10.9, 'SE', '2024-03-05 03:15:00.456'),
  (6, 29.0, 63.4, 1017.3, 7.5, 15.3, 'E', '2024-03-05 05:00:00.789'),
  (7, 18.4, 48.3, 1008.7, 1.5, 7.2, 'N', '2024-03-05 07:30:00.987'),
  (7, 23.0, 54.2, 1011.4, 3.3, 9.5, 'S', '2024-03-05 09:15:00.234'),
  (7, 26.5, 61.6, 1013.9, 5.1, 12.7, 'W', '2024-03-05 11:00:00.567'),
  (7, 17.9, 45.8, 1007.5, 1.0, 6.8, 'NE', '2024-03-05 13:30:00.876'),
  (1, 21.7, 52.4, 1010.9, 2.8, 8.9, 'SW', '2024-03-05 15:00:00.345'),
  (1, 27.8, 59.3, 1016.0, 6.7, 14.0, 'NW', '2024-03-05 17:45:00.654'),
  (2, 19.2, 47.6, 1009.0, 1.3, 7.6, 'SE', '2024-03-05 19:30:00.789');
