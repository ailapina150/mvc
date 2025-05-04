package com.resume.model;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "employees", schema ="public" )
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name ="english_level")
    @Enumerated(EnumType.ORDINAL)
    private EnglishLevels englishLevel;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    private List<Education> educations;
}
