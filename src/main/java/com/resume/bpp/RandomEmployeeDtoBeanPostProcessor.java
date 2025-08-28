package com.resume.bpp;

import com.resume.annotations.RandomEmployeeDto;
import com.resume.dto.EmployeeDto;
import com.resume.utils.Random;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class RandomEmployeeDtoBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(RandomEmployeeDto.class)
                    && field.getType().getName().equals(EmployeeDto.class.getName())) {
                field.setAccessible(true);
                try {
                    field.set(bean, Random.getRandomEmployeeDto());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        return bean;
    }

}
