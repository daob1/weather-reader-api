CREATE TABLE IF NOT EXISTS sensors (
    sensor_id INT PRIMARY KEY AUTO_INCREMENT,
    sensor_name VARCHAR(255) NOT NULL,
    sensor_description TEXT,
    manufacturer VARCHAR(100),
    install_date TIMESTAMP(3),
    location_id INT NOT NULL, -- Foreign key to the locations table
    created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP AT TIME ZONE 'UTC',
    updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP AT TIME ZONE 'UTC' ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS locations (
    location_id INT PRIMARY KEY AUTO_INCREMENT,
    location_name VARCHAR(255) NOT NULL,
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    address VARCHAR(255),
    country_iso_2_code VARCHAR(255),
    created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP AT TIME ZONE 'UTC',
    updated_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP AT TIME ZONE 'UTC' ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS readings (
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