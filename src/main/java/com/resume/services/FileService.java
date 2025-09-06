package com.resume.services;

import com.example.errorhandlerstarter.exception.ResourceNotFoundException;
import com.resume.dto.EmployeeDto;
import com.resume.model.FileFormat;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;


@Slf4j
@Service
@AllArgsConstructor
public class FileService {

//    private final FileGenerateFactory fileGenerateFactory;
//
//    public String createFile(EmployeeDto employee, FileFormat fileFormat) {
//        FileGenerator generator = fileGenerateFactory.getFileGenerator(fileFormat);
//        return generator.generateFile(employee);
//    }

    private final FileCashService service;

    private final CacheManager cacheManager;

    public String createFile(EmployeeDto employee, FileFormat fileFormat)  {
        String filePath = service.createFile(employee, fileFormat);
        if (!Files.exists(Path.of(filePath))) {
            evictFromCache(employee.getId(), fileFormat);
            filePath = service.createFile(employee, fileFormat);
            if (!Files.exists(Path.of(filePath))){
                try {
                    throw new ResourceNotFoundException("File not found");
                } catch (Exception e) {
                   log.error(e.getMessage());
                }
            }

        }
        return filePath;
    }

    private void evictFromCache(Long employeeId, FileFormat fileFormat) {
        String cacheKey = employeeId + "-" + fileFormat.name();
        Cache cache = cacheManager.getCache(FileCashService.CACHE_NAME);
        if (cache != null) {
            cache.evict(cacheKey);
        }
    }
}
