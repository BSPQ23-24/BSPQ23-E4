package com.example.server.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.server.entity.Car;
import com.example.server.service.CarService;
import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.data.EvaluationContext;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.Rule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CarControllerPerfTest {

    @Rule
    public JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("performance_reports"));

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController controller;

    @BeforeEach
    public void setUp() {
        Car car1 = new Car();
        Car car2 = new Car();
        when(carService.getAllCars()).thenReturn(Arrays.asList(car1, car2));
    }

    @Test
    @JUnitPerfTest(threads = 50, durationMs = 10_000, warmUpMs = 5_000, maxExecutionsPerSecond = 1000)
    public void testGetAllCarsPerformance() {
        ResponseEntity<List<Car>> response = controller.getAllCars();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }
}
