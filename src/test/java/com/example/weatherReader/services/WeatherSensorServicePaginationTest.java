package com.example.weatherReader.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherSensorServicePaginationTest {

    @Test
    public void testCalculateOffsetPageNum1() {
        int pageNum = 1;
        int pageSize = 10;

        int offset = WeatherSensorService.calculateOffset(pageNum, pageSize);

        assertEquals(0, offset);
    }

    @Test
    public void testCalculateOffsetPageNum2() {
        int pageNum = 2;
        int pageSize = 10;

        int offset = WeatherSensorService.calculateOffset(pageNum, pageSize);

        assertEquals(10, offset);
    }

    @Test
    public void testCalculateOffsetPageNumGreaterThan1() {
        int pageNum = 3;
        int pageSize = 10;

        int offset = WeatherSensorService.calculateOffset(pageNum, pageSize);

        assertEquals(20, offset);
    }

}