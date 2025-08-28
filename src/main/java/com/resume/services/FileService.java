package com.resume.services;

import com.resume.dto.EmployeeDto;
import com.resume.model.FileFormat;
import com.resume.services.fileFabrica.FileGenerateFactory;
import com.resume.services.fileFabrica.FileGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileService {

    FileGenerateFactory fileGenerateFactory;

    public String createFile(EmployeeDto employee, FileFormat fileFormat) {
        FileGenerator generator = fileGenerateFactory.getFileGenerator(fileFormat);
       return generator.generateFile(employee);
    }
}
