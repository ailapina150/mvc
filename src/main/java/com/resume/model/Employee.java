package com.resume.model;
import lombok.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "employees", schema ="public" )
public class Employee {
    @Id
    private Long id;
    private String name;
    private String position;
    private String format;

    private String photo;

    private String email;
    private String tg;
    private Long phone;

    private String summary;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Project> projects;

    @Transient
    private List<Skills> skills;

    private EnglishLevels englishLevel;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Education> educations;
}
