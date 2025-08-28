package com.resume.services.fileFabrica;

import com.resume.dto.EmployeeDto;
import org.springframework.stereotype.Component;


public interface FileGenerator {
    String FOLDER_PATH = "c:\\Users\\PC\\Downloads\\";

    String generateFile(EmployeeDto employee);
}
