package com.resume.fabrica;

import com.resume.model.Employee;

public interface FileGenerator {
    public final String FOLDER_PATH = "c:\\Users\\PC\\Downloads\\";
    String generateFile(Employee employee);
}
