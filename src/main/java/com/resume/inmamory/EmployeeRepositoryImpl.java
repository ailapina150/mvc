package com.resume.inmamory;

import com.resume.Port;
import com.resume.model.*;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.List.*;

@Repository
public class EmployeeRepositoryImpl implements Port<Employee> {
    List<Employee> EMPLOYEES = new ArrayList<>();
    {
        Employee employee1 = Employee
                .builder()
                .id(1L)
                .name("Hanna Lapina")
                .position("Java Developer")
                .format("remotely or hybrid from Brest, Belarus")
                .photo("/images/img_1.png")
                .tg("@HanaLapina")
                .phone(375_298_211_966L)
                .email("alapina840@gmail.com")
                .summary("""
                        I have been developing applications in Java and Sprig since 2021.
                        I have experience in optimizing high-load microservice applications,
                        as well as creating applications from scratch
                        """)
                .educations(List.of(
                      Education.builder()
                              .id(1)
                              .yearStart(2001)
                              .yearEnd(2006)
                              .university( "Brest State Technical University")
                              .degree("Civil engineer")
                              .build(),
                        Education.builder()
                                .id(2)
                                .yearStart(2006)
                                .yearEnd(2007)
                                .university( "Brest State Technical University. Magistracy")
                                .degree("Master of Technical Sciences")
                                .build(),
                        Education.builder()
                                .id(3)
                                .yearStart(2007)
                                .yearEnd(2009)
                                .university( "Brest State Technical University")
                                .degree("Software engineer")
                                .build()
                ))
                .projects(of(
                        Project.builder()
                                .name("Project for the Ministry of Nature of the Republic of Belarus")
                                .description("Microservice RestFul web application")
                                .tasks(of(
                                        Task.builder().description("Developing a microservice responsible for creating reports (using SpringBoot)").build(),
                                        Task.builder().description("Outputting reports to Excel and Word using the Apache POI library, working with the Jasper Reports application").build(),
                                        Task.builder().description("Writing complex queries to the database in native SQL (PostgreSQL)").build(),
                                        Task.builder().description("Mapping entities to Java objects (Hibernate)").build(),
                                        Task.builder().description("Determining performance bottlenecks and optimizing SQL queries").build(),
                                        Task.builder().description("Working with Stream API and multithreading").build(),
                                        Task.builder().description("Working with message brokers (Kafka)").build(),
                                        Task.builder().description("Working with a noSQL database (Redis)").build(),
                                        Task.builder().description("Connecting Swagger for automatic generation of API descriptions").build(),
                                        Task.builder().description("Code review and code refactoring").build(),
                                        Task.builder().description("Creating algorithms for statistical and probabilistic calculations").build(),
                                        Task.builder().description("Assistance to analysts in creating technical specifications").build()
                                ))
                                .build(),
                        Project.builder()
                                .name("Project for the Ministry of Transport of the Republic of Belarus")
                                .description("Monolithic RestFul web application")
                                .tasks(of(
                                        Task.builder().description("Creating a demo application from scratch").build(),
                                        Task.builder().description("Development of calculation algorithms").build(),
                                        Task.builder().description("Development of an architectural solution using the MVC pattern").build(),
                                        Task.builder().description("Development of a REST API").build(),
                                        Task.builder().description("Database design").build(),
                                        Task.builder().description("Deployment of the database in Docker").build(),
                                        Task.builder().description("Implementation of all application logic").build(),
                                        Task.builder().description("Writing build scripts on maven").build()
                                ))
                                .build(),
                        Project.builder()
                                .name("Car leasing project")
                                .description("Microservice desktop application")
                                .tasks(of(
                                        Task.builder().description("Working with multithreading").build(),
                                        Task.builder().description("Working with message brokers (RabbitMQ)").build(),
                                        Task.builder().description("Writing JUnit and integration tests (parameterized tests, Mockito, TestContaners)").build(),
                                        Task.builder().description("Working on SCRUM methodology in a team of 6 people").build()
                                ))
                                .build()
                ))
                .skills(of(Skills.JAVA, Skills.SPRING_CORE,Skills.SPRING_BOOT, Skills.SPRING_CLOUD,
                        Skills.SPRING_DATA, Skills.SPRING_AOP, Skills.HIBERNATE, Skills.LOMBOK, Skills.SWAGGER,
                        Skills.POSTGRESQL, Skills.MYSQL, Skills.REDIS, Skills.GRADLE, Skills.MAVEN, Skills.GIT,
                        Skills.JUNIT, Skills.MOCKITO, Skills.TEST_CONTAINERS, Skills.KAFKA, Skills.RABBIT_MQ,
                        Skills.OOP, Skills.GOF, Skills.REST, Skills.DRY, Skills.KISS, Skills.SOLID))
                .englishLevel(EnglishLevels.B1)
                .build();

        Employee employee2 = Employee
                .builder()
                .id(2L)
                .name("Mary Poppinss")
                .position("Java Developer")
                .photo("/images/img_2.png")
                .format("remotely or hybrid from Brest, Belarus")
                .tg("@HanaLapina")
                .phone(375_298_211_966L)
                .email("alapina840@gmail.com")
                .summary("""
                        I have been developing applications in Java and Sprig since 2021.
                        I have experience in optimizing high-load microservice applications,
                        as well as creating applications from scratch
                        """)
                .educations(of(
                        Education.builder()
                                .id(1)
                                .yearStart(2001)
                                .yearEnd(2006)
                                .university( "Brest State Technical University")
                                .degree("Civil engineer")
                                .build(),
                        Education.builder()
                                .id(2)
                                .yearStart(2006)
                                .yearEnd(2007)
                                .university( "Brest State Technical University. Magistracy")
                                .degree("Master of Technical Sciences")
                                .build()
                ))
                .projects(of(
                        Project.builder()
                                .name("Car leasing project")
                                .description("Microservice desktop application")
                                .tasks(of(
                                        Task.builder().description("Working with multithreading").build(),
                                        Task.builder().description("Working with message brokers (RabbitMQ)").build(),
                                        Task.builder().description("Writing JUnit and integration tests (parameterized tests, Mockito, TestContaners)").build(),
                                        Task.builder().description("Working on SCRUM methodology in a team of 6 people").build()
                                ))
                                .build()
                ))
                .skills(of(Skills.JAVA, Skills.SPRING_CORE,Skills.SPRING_BOOT, Skills.SPRING_CLOUD,
                        Skills.SPRING_DATA, Skills.SPRING_AOP, Skills.HIBERNATE, Skills.LOMBOK))
                .englishLevel(EnglishLevels.C1)
                .build();
        EMPLOYEES.add(employee1);
        EMPLOYEES.add(employee2);
    }

    public Optional<Employee> findById(Long id){
        return EMPLOYEES.stream().filter(employee -> employee.getId().equals(id)).findAny();
    }

    @Override
    public Employee save(Employee employee) {
        if(employee!=null) {
            EMPLOYEES.removeIf(b -> b.getId().equals(employee.getId()));
        }
        EMPLOYEES.add(employee);
        return employee;
    }

    @Override
    public void deleteById(Long id) {
        EMPLOYEES.removeIf(book -> book.getId().equals(id));

    }

    public List<Employee> findAll(){
        return EMPLOYEES;
    }
}
