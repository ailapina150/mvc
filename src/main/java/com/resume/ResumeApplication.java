package com.resume;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Slf4j
@SpringBootApplication
@EnableRedisRepositories(basePackages = "com.resume.redisRepository")
@EnableJpaRepositories(basePackages = "com.resume.jpaRepositories")
@EnableCaching
public class ResumeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResumeApplication.class, args);
        log.warn("---------APPLICATION STARTED------------");
        log.info("---------APPLICATION STARTED------------");
        log.debug("---------APPLICATION STARTED------------");
    }
}
