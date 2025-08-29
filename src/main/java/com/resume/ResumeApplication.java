package com.resume;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ResumeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResumeApplication.class, args);
        log.warn("---------APPLICATION STARTED------------");
        log.info("---------APPLICATION STARTED------------");
        log.debug("---------APPLICATION STARTED------------");
    }
}
