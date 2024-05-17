package com.example.server.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.server.entity.Rental;
import com.example.server.service.RentalService;
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

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RentalControllerPerfTests {

    @Rule
    public JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("performance_reports"));

    @Mock
    private RentalService rentalService;

    @InjectMocks
    private RentalController controller;

    @BeforeEach
    public void setUp() {
        Rental rental1 = new Rental();
        Rental rental2 = new Rental();
        when(rentalService.getAllRental()).thenReturn(Arrays.asList(rental1, rental2));
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 5_000, warmUpMs = 2_000, maxExecutionsPerSecond = 50)
    public void testGetAllRentalsPerformance() {
        List<Rental> rentals = controller.getAllRentals();
        assertNotNull(rentals);
        assertEquals(2, rentals.size());
    }
}
