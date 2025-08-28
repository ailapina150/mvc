package com.resume.config;

import com.resume.dto.EmployeeDto;
import com.resume.dto.ProjectDto;
import com.resume.utils.Random;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
public class DtoConfig {

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public ProjectDto randomProjectDto(){
        return Random.getRandomProjectDto();
    }


    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public EmployeeDto randomEmployeeDto() {
        return Random.getRandomEmployeeDto();
    }

}
