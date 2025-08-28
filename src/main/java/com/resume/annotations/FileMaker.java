package com.resume.annotations;

import com.resume.model.FileFormat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileMaker {
    FileFormat fileFormat() default FileFormat.EXCEL; //
    String folderName() default "c:\\Users\\PC\\Downloads\\";// Исправлено: должно быть методом
}
