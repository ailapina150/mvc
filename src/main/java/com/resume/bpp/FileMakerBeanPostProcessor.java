package com.resume.bpp;

import com.resume.annotations.FileMaker;
import com.resume.model.FileFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Component
public class FileMakerBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(FileMaker.class) &&
                    method.getReturnType().equals(String.class) &&
                    method.getParameterCount() == 1) {
                FileMaker fileMaker = method.getAnnotation(FileMaker.class);
                return switch (fileMaker.fileFormat()) {
                    case FileFormat.DOCX -> createProxyForDoc(bean, method);
                    case FileFormat.EXCEL -> createProxyForExl(bean, method);
                };
            }
        }
        return bean;
    }


    private Object createProxyForExl(Object target, Method targetMethod) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (method.equals(targetMethod)) {
                FileMaker annotation = method.getAnnotation(FileMaker.class);
                Object argument = args[0];
                String fileName = annotation.folderName();
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("sheet1");

                int rowNum = 0;
                for (Field field : argument.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(field.getName());
                    try {
                        String value = field.get(argument).toString();
                        row.createCell(1).setCellValue(value);
                        if (rowNum == 1) {
                            fileName = fileName + value + ".xlsx";
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                    workbook.write(fileOutputStream);
                    fileOutputStream.close();
                    return fileName;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return proxy.invoke(target, args);
        });

        return enhancer.create();
    }

    private Object createProxyForDoc(Object target, Method targetMethod) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (method.equals(targetMethod)) {
                FileMaker annotation = method.getAnnotation(FileMaker.class);
                Object argument = args[0];
                XWPFDocument doc = new XWPFDocument();
                XWPFParagraph paragraph = doc.createParagraph();
                String firstFieldValue = null;
                for (Field field : argument.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    XWPFRun run = paragraph.createRun();
                    try {
                        String value = field.get(argument).toString();
                        run.setText(field.getName() + ": " + value);
                        run.addBreak();
                        if (firstFieldValue == null) {
                            firstFieldValue = value;
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                try {
                    String fileName = annotation.folderName() +
                            (firstFieldValue != null ? firstFieldValue : "data") +
                            ".docx";
                    FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                    doc.write(fileOutputStream);
                    fileOutputStream.close();
                    return fileName;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return proxy.invoke(target, args);
        });

        return enhancer.create();
    }


}
