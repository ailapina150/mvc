package com.resume.services;

import com.resume.dto.EmployeeDto;
import com.resume.model.FileFormat;
import com.resume.services.fileFabrica.FileGenerateFactory;
import com.resume.services.fileFabrica.FileGenerator;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileCashService {
    public static final String CACHE_NAME = "generatedFiles";

    private final FileGenerateFactory fileGenerateFactory;

    @Cacheable(value = "generatedFiles", key = "#employee.id + '-' + #fileFormat.name()",
            unless = "#result == null || #result.isEmpty()")
    public String createFile(EmployeeDto employee, FileFormat fileFormat) {
        FileGenerator generator = fileGenerateFactory.getFileGenerator(fileFormat);
        return generator.generateFile(employee);
    }
}
