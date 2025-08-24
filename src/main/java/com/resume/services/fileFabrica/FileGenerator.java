package com.resume.services.fileFabrica;

import com.resume.model.Employee;

public interface FileGenerator {
    String FOLDER_PATH = "c:\\Users\\PC\\Downloads\\";

    String generateFile(Employee employee);
}
