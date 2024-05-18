package com.example.server.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.server.entity.User;
import com.example.server.service.UserService;
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
public class UserControllerPerfTests {

    @Rule
    public JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("performance_reports"));

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    public void setUp() {
        User user1 = new User();
        User user2 = new User();
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 5_000, warmUpMs = 2_000, maxExecutionsPerSecond = 50)
    public void testGetAllUsersPerformance() {
        ResponseEntity<List<User>> response = controller.getAllUsers();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

}
