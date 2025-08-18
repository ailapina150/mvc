package com.resume.services;

import com.resume.model.Employee;
import com.resume.fabrica.FileFormat;
import com.resume.fabrica.FileGenerateFactory;
import com.resume.fabrica.FileGenerator;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    public String createFile(Employee employee, FileFormat fileFormat) {
        FileGenerator generator = FileGenerateFactory.getFileGenerator(fileFormat);
        return generator.generateFile(employee);
    }
}
