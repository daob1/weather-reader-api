Weather Reading REST API

This is a simple RESTful API for managing weather readings from various sensors. The API is implemented in Java 21 and uses the Spring Boot framework.
Features

    Create: Register New Sensor data
    Read: Retrieve weather readings based on sensors, time range, or other criteria.

Technologies Used

    Java 21
    Spring boot 3
    [SQL] (h2)
    Swagger
    Maven

Installation

Clone the repository:

    git clone https://github.com/daob1/weatherReader-api.git

Build the project:

    mvn clean install

Run the application:

    ./mvnw spring-boot:run

The API will be accessible at http://localhost:8080.

Dockerhub image: 
    
    https://hub.docker.com/repository/docker/daob1/weather-reader-api/

Docker commands:

    docker build -t weather-reader-api .

    docker run -p 8080:8080 -e "JAVA_OPTS=-Ddebug -Xmx128m" weather-reader-api

Request and Response Examples

To create a weather sensor reading

    GET http://localhost:8080/v1/sensor?sensorId=1

To get a paginated list of the weather sensor readings

    GET http://localhost:8080/v1/sensorReadings?page=1&pageSize=15

To create a weather sensor reading
    
    POST http://localhost:8080/v1/sensorReading
    {
	"sensorId":3,
	"temperatureCelsius":19.2,
	"humidity":47.6,
	"pressure":1009,
	"rainFallMm":1.3,
	"windSpeed":7.6,
	"windDirection":"SE",
	"readingTime": "2024-03-07T17:44:03.333Z"
    }

Check out my swag:

    Swagger UI: http://localhost:8080/swagger-ui/index.html



Additional Notes: Currently WIP. The database is wiped each time the application starts. This is intentional to help manual testing.