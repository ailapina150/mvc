package com.resume.model;
import lombok.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class Employee {
    private Long id;
    private String name;
    private String position;
    private String format;

    private String photo;

    private String email;
    private String tg;
    private Long phone;

    private String summary;

    private List<Project> projects;
    private List<Skills> skills;
    private EnglishLevels englishLevel;
    private List<Education> educations;
}
