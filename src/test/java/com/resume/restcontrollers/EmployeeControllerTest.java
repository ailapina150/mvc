package com.resume.restcontrollers;

import com.resume.annotations.RandomEmployeeDto;
import com.resume.dto.EmployeeDto;
import com.resume.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService service;

    @RandomEmployeeDto
    private EmployeeDto employee;

    @BeforeEach
    void checkConnection() {
        try (Connection conn = dataSource.getConnection()) {
            assertTrue(conn.isValid(2)); // Verify connection works
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }

    @BeforeEach
    void setUp() throws IllegalAccessException {
        service.deleteAll();
        employee = service.save(employee);
    }

    @Test
    void getEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", employee.getId()))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(employee.getId()),
                        jsonPath("$.name").value(employee.getName()),
                        jsonPath("$.email").value(employee.getEmail())
                );
        System.out.println("Employee: " + employee);
    }

    @Test
    void getAllEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.size()").value(1)
                );
    }

    @Test
    void getEmployeeNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/{id}", 999L))
                .andExpectAll(
                        status().isNotFound()
                );
    }
}
