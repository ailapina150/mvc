package com.resume.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // Можно использовать на методах
@Retention(RetentionPolicy.RUNTIME) // Доступна в runtime
public @interface SimpleLog {
    String value() default ""; // Необязательный параметр
}
