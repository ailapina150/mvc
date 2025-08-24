package com.resume.services;

import com.resume.model.Employee;
import com.resume.model.FileFormat;
import com.resume.services.fileFabrica.FileGenerateFactory;
import com.resume.services.fileFabrica.FileGenerator;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    public String createFile(Employee employee, FileFormat fileFormat) {
        FileGenerator generator = FileGenerateFactory.getFileGenerator(fileFormat);
        return generator.generateFile(employee);
    }
}
