package com.resume.services;

import com.resume.dto.EmployeeDto;
import com.resume.model.FileFormat;
import com.resume.services.fileFabrica.FileGenerateFactory;
import com.resume.services.fileFabrica.FileGenerator;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    public String createFile(EmployeeDto employee, FileFormat fileFormat) {
        FileGenerator generator = FileGenerateFactory.getFileGenerator(fileFormat);
        return generator.generateFile(employee);
    }
}
