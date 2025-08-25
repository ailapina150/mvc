package com.resume.restcontrollers;

import com.resume.dto.EmployeeDto;
import com.resume.model.EnglishLevels;
import com.resume.services.EmployeeService;
import com.resume.springdatarepositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private EmployeeRepository repository;

    private Long employeeId;


    @BeforeEach
    void checkConnection() {
        try (Connection conn = dataSource.getConnection()) {
            assertTrue(conn.isValid(2)); // Verify connection works
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }

    @BeforeEach
    void setUp() {
        // Очищаем всю базу перед каждым тестом
        repository.deleteAll();
        // Создание тестовых данных
        EmployeeDto employee = new EmployeeDto();
        employee.setName("John Doe");
        employee.setPosition("java developer");
        employee.setFormat("remotely or hybrid from Brest, Belarus");
        employee.setPhoto("/images/img_1.png");
        employee.setTg("@HanaLapina");
        employee.setPhone(375_298_211_966L);
        employee.setSummary("""
                I have been developing applications in Java and Sprig since 2021.
                I have experience in optimizing high-load microservice applications,
                as well as creating applications from scratch
                """);
        employee.setEmail("john@example.com");
        employee.setEnglishLevel(EnglishLevels.B1);
        EmployeeDto savedEmployee = service.save(employee);
        employeeId = savedEmployee.getId();
    }

    @Test
    void getEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", employeeId))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(employeeId),
                        jsonPath("$.name").value("John Doe"),
                        jsonPath("$.email").value("john@example.com")
                );
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
