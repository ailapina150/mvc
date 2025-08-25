package com.resume.services.fileFabrica;

import com.resume.dto.EmployeeDto;

public interface FileGenerator {
    String FOLDER_PATH = "c:\\Users\\PC\\Downloads\\";

    String generateFile(EmployeeDto employee);
}
