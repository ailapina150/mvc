package com.resume.bpp;

import com.resume.annotations.RandomProjectDto;
import com.resume.dto.ProjectDto;
import com.resume.utils.Random;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class RandomProjectBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(RandomProjectDto.class)
            && field.getType().getName().equals(ProjectDto.class.getName())) {
                field.setAccessible(true);
                try {
                    field.set(bean, Random.getRandomProjectDto());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        return bean;
    }



}
