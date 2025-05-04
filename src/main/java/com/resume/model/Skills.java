package com.resume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Skills {
    JAVA("Java"),
    JAVA_SCRIPT("JavaScript"),
    PYTHON("Python"),
    SPRING_CORE("Spring Core"),
    SPRING_BOOT("Spring Boot"),
    SPRING_DATA("Spring Data"),
    SPRING_WEB("Spring Web"),
    SPRING_TEST("Spring Test"),
    SPRING_CLOUD("Spring Cloud"),
    SPRING_AOP("Spring AOP"),
    SPRING_SECURITY("Spring Security"),
    VIEW("View"),
    ANGULAR("Angular"),
    REACT("React"),
    REDUX("Redux"),
    LOMBOK("Lombok"),
    HIBERNATE("Hibernate"),
    JUNIT("Junit"),
    MOCKITO("Mockito"),
    TEST_CONTAINERS("TestContainers"),
    SWAGGER("Swagger"),
    GIT("Git"),
    MAVEN("Maven"),
    GRADLE("Cradle"),
    GITLAB_CI_CD("GitLab CI/CD"),
    DOCKER("Docker"),
    KUBERNETES("Kubernetes"),
    KAFKA("Kafka"),
    RABBIT_MQ("RabbitMQ"),
    REST("REST"),
    OOP("OOP"),
    SOLID("SOLID"),
    DRY("DRY"),
    KISS("KISS"),
    GOF("GoF"),
    MYSQL("MySQL"),
    POSTGRESQL("PostgreSQL"),
    REDIS("Redis");
    private String title;

    public static List<String> titles(){
        return Arrays.stream(values()).map(Skills::getTitle).toList();
    }

}
